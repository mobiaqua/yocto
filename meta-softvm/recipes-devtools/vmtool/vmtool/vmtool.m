/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice (including the next
 * paragraph) shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */

#import <Foundation/Foundation.h>
#import <Virtualization/Virtualization.h>

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <termios.h>
#include <getopt.h>

#define VSOCK_PORT 6666

int socket_fd = -1;

@interface VZVirtioSocketListenerDelegateImpl : NSObject <VZVirtioSocketListenerDelegate>

@end

@implementation VZVirtioSocketListenerDelegateImpl

- (BOOL)listener:(VZVirtioSocketListener *)listener
		shouldAcceptNewConnection:(VZVirtioSocketConnection *)connection
		fromSocketDevice:(VZVirtioSocketDevice *)socketDevice; {
	if (connection.destinationPort == VSOCK_PORT) {
		socket_fd = dup(connection.fileDescriptor);
		return YES;
	} else {
		return NO;
	}
}

@end

static void help(void)
{
	fprintf(stderr,
			"vmtool\n\n"
			"USAGE: vmtool <options>\n\n"
			"OPTIONS:\n\n"
			"\t-k, --kernel=PATH\n"
			"\t\t kernel image file path, required\n\n"
			"\t-a, --append=\"KERNEL_ARGS\"\n"
			"\t\t kernel cmdline arguments, defaults: \"console=hvc0\"\n\n"
			"\t-p, --cpu=NUM_CPU\n"
			"\t\t number of CPUs, defaults: 1\n\n"
			"\t-m, --mem=NUM_MEM\n"
			"\t\t memory size in MB, defaults: 512\n\n"
			"\t-r, --rootfs=ROOTFS_PATH\n"
			"\t\t create virtiofs with given path\n"
			"\t\t and append kernel cmdline argument: \"root=ROOTFS rootfstype=virtiofs\"\n\n"
			"\t-r, --toolsfs=TOOLS_FS\n"
			"\t\t create virtiofs with given path\n"
			"\t\t and append kernel cmdline argument: \"tdir=<tools path>\"\n\n"
			"\t-r, --workspacefs=WORKSPACE_FS\n"
			"\t\t create virtiofs with given path\n"
			"\t\t and append kernel cmdline argument: \"wdir=<workspace path>\"\n\n"
			"\t-r, --currentfs=CURRENT_FS\n"
			"\t\t create virtiofs with given path\n"
			"\t\t and append kernel cmdline argument: \"cdir=<current path>\"\n\n"
			);
}

int main(int argc, char *argv[]) {
	@autoreleasepool {
		VZVirtualMachine *vm;
		VZVirtualMachineConfiguration *vm_config;
		VZLinuxBootLoader *vm_bootloader;
		VZSerialPortAttachment *vm_serial_port;
		VZVirtioConsoleDeviceSerialPortConfiguration *vm_console_configuration;
		VZVirtioSocketDeviceConfiguration *vm_socket;
		__block VZVirtioSocketListenerDelegateImpl *socket_listener_delegate;
		NSError *vm_error = NULL;
		NSString *kernel_path = NULL;
		NSString *append_cmdline;
		NSString *rootfs_path = NULL;
		NSString *tools_path = NULL;
		NSString *workspace_path = NULL;
		NSString *current_path = NULL;
		NSString *append_virtiofs = [NSMutableString stringWithString:@""];
		NSArray *dir_shares = @[];
		NSFileHandle *terminal_input, *terminal_output;
		struct termios terminal;
		int cpus = 1;
		int mem = 512;

		uint min_cpu = (uint)VZVirtualMachineConfiguration.minimumAllowedCPUCount;
		uint max_cpu = (uint)VZVirtualMachineConfiguration.maximumAllowedCPUCount;
		uint min_mem = (uint)(VZVirtualMachineConfiguration.minimumAllowedMemorySize / 1024 / 1024);
		uint max_mem = (uint)(VZVirtualMachineConfiguration.maximumAllowedMemorySize / 1024 / 1024);

		static struct option long_options[] = {
			{"kernel",       required_argument, NULL, 'k'},
			{"append",       required_argument, NULL, 'a'},
			{"cpu",          required_argument, NULL, 'p'},
			{"mem",          required_argument, NULL, 'm'},
			{"rootfs",       required_argument, NULL, 'r'},
			{"toolsfs",      required_argument, NULL, 't'},
			{"workspacefs",  required_argument, NULL, 'w'},
			{"currentfs",    required_argument, NULL, 'c'},
			{"help",         no_argument,       NULL, 'h'},
			{NULL,           0,                 NULL,  0 }
		};

		int option, option_index = 0;
		while ((option = getopt_long(argc, argv, "k:a:p:m:r:t:w:c:h", long_options, &option_index)) != -1) {
			switch (option) {
				case 'k':
					kernel_path = [NSString stringWithUTF8String:optarg];
					break;
				case 'a':
					append_cmdline = [NSMutableString stringWithUTF8String:optarg];
					break;
				case 'p':
					cpus = atoi(optarg);
					if (cpus < min_cpu || cpus > max_cpu) {
						help();
						fprintf(stderr, "\nError: Wrong number of CPUs, valid range: %d - %d\n\n", min_cpu, max_cpu);
						return EXIT_FAILURE;
					}
					break;
				case 'm':
					mem = atoi(optarg);
					if (mem < min_mem || mem > max_mem) {
						help();
						fprintf(stderr, "\nError: Wrong amount of memory, valid range: %dMB - %dMB\n\n", min_mem, max_mem);
						return EXIT_FAILURE;
					}
					break;
				case 'r':
					rootfs_path = [NSString stringWithUTF8String:optarg];
					if (access(optarg, R_OK) < 0) {
						help();
						fprintf(stderr, "\nError: Root FS path is wrong or not accessible\n\n");
						return EXIT_FAILURE;
					}
					append_virtiofs = [append_virtiofs stringByAppendingString: @" root=ROOTFS rootfstype=virtiofs"];
					break;
				case 't':
					tools_path = [NSString stringWithUTF8String:optarg];
					if (access(optarg, R_OK) < 0) {
						help();
						fprintf(stderr, "\nError: tools path is wrong or not accessible\n\n");
						return EXIT_FAILURE;
					}
					append_virtiofs = [append_virtiofs stringByAppendingFormat:@" tdir=%@", tools_path];
					break;
				case 'w':
					workspace_path = [NSString stringWithUTF8String:optarg];
					if (access(optarg, R_OK) < 0) {
						help();
						fprintf(stderr, "\nError: workspace path is wrong or not accessible\n\n");
						return EXIT_FAILURE;
					}
					append_virtiofs = [append_virtiofs stringByAppendingFormat:@" wdir=%@", workspace_path];
					break;
				case 'c':
					current_path = [NSString stringWithUTF8String:optarg];
					if (access(optarg, R_OK) < 0) {
						help();
						fprintf(stderr, "\nError: current path is wrong or not accessible\n\n");
						return EXIT_FAILURE;
					}
					append_virtiofs = [append_virtiofs stringByAppendingFormat:@" cdir=%@", current_path];
					break;
				case 'h':
					help();
					return EXIT_SUCCESS;
				case '?':
				default:
					help();
					return EXIT_FAILURE;
			}
		}

		if (!kernel_path) {
			help();
			fprintf(stderr, "\nError: Missing kernel image path parameter!\n\n");
			return EXIT_FAILURE;
		}
		if (access(kernel_path.UTF8String, R_OK) < 0) {
			fprintf(stderr, "\nError: Kernel image file not found or not accessible!\n\n");
			return EXIT_FAILURE;
		}

		if (!append_cmdline)
			append_cmdline = @"console=hvc0";

		append_cmdline = [append_cmdline stringByAppendingString:append_virtiofs];

		if (rootfs_path) {
			VZVirtioFileSystemDeviceConfiguration *virtiofs_conf = [[VZVirtioFileSystemDeviceConfiguration alloc] initWithTag:@"ROOTFS"];
			virtiofs_conf.share = [[VZSingleDirectoryShare alloc] initWithDirectory: [[VZSharedDirectory alloc] initWithURL:[NSURL fileURLWithPath:rootfs_path] readOnly:false]];
			dir_shares = [dir_shares arrayByAddingObject:virtiofs_conf];
		}

		if (tools_path) {
			VZVirtioFileSystemDeviceConfiguration *virtiofs_conf = [[VZVirtioFileSystemDeviceConfiguration alloc] initWithTag:@"TOOLSFS"];
			virtiofs_conf.share = [[VZSingleDirectoryShare alloc] initWithDirectory: [[VZSharedDirectory alloc] initWithURL:[NSURL fileURLWithPath:tools_path] readOnly:false]];
			dir_shares = [dir_shares arrayByAddingObject:virtiofs_conf];
		}

		if (workspace_path) {
			VZVirtioFileSystemDeviceConfiguration *virtiofs_conf = [[VZVirtioFileSystemDeviceConfiguration alloc] initWithTag:@"WORKSPACEFS"];
			virtiofs_conf.share = [[VZSingleDirectoryShare alloc] initWithDirectory: [[VZSharedDirectory alloc] initWithURL:[NSURL fileURLWithPath:workspace_path] readOnly:false]];
			dir_shares = [dir_shares arrayByAddingObject:virtiofs_conf];
		}

		if (current_path) {
			VZVirtioFileSystemDeviceConfiguration *virtiofs_conf = [[VZVirtioFileSystemDeviceConfiguration alloc] initWithTag:@"CURRENTFS"];
			virtiofs_conf.share = [[VZSingleDirectoryShare alloc] initWithDirectory: [[VZSharedDirectory alloc] initWithURL:[NSURL fileURLWithPath:current_path] readOnly:false]];
			dir_shares = [dir_shares arrayByAddingObject:virtiofs_conf];
		}

		terminal_input = [[NSFileHandle alloc] initWithFileDescriptor:0];
		terminal_output = [[NSFileHandle alloc] initWithFileDescriptor:1];
		tcgetattr(terminal_input.fileDescriptor, &terminal);
		terminal.c_iflag &= ~(ICRNL);
		terminal.c_lflag &= ~(ICANON | ECHO);
		tcsetattr(terminal_input.fileDescriptor, TCSANOW, &terminal);

		vm_serial_port = [[VZFileHandleSerialPortAttachment alloc]
						  initWithFileHandleForReading:terminal_input
						  fileHandleForWriting:terminal_output];

		vm_console_configuration = [[VZVirtioConsoleDeviceSerialPortConfiguration alloc] init];
		[vm_console_configuration setAttachment:vm_serial_port];

		vm_socket = [[VZVirtioSocketDeviceConfiguration alloc] init];

		vm_bootloader = [[VZLinuxBootLoader alloc] initWithKernelURL:[NSURL fileURLWithPath:kernel_path]];
		[vm_bootloader setCommandLine:append_cmdline];

		vm_config = [VZVirtualMachineConfiguration new];
		[vm_config setBootLoader:vm_bootloader];
		[vm_config setCPUCount:cpus];
		[vm_config setMemorySize:mem * 1024 * 1024UL];
		[vm_config setSerialPorts:@[vm_console_configuration]];
		[vm_config setDirectorySharingDevices:dir_shares];
		[vm_config setSocketDevices:@[vm_socket]];

		[vm_config validateWithError:&vm_error];
		if (vm_error) {
			fprintf(stderr, "\nError: VM configuration is not valid! \n\t%s\n\n", [vm_error localizedDescription].UTF8String);
			return EXIT_FAILURE;
		}

		dispatch_queue_t queue = dispatch_queue_create(NULL, DISPATCH_QUEUE_SERIAL);

		vm = [[VZVirtualMachine alloc] initWithConfiguration:vm_config queue:queue];
		dispatch_sync(queue, ^{
			if (!vm.canStart) {
				fprintf(stderr, "\nError: VM can not start! \n\t%s\n\n", [vm_error localizedDescription].UTF8String);
				exit(EXIT_FAILURE);
			}

			[vm startWithCompletionHandler:^(NSError *error) {
				if (error) {
					fprintf(stderr, "\nError: VM failed to start! \n\t%s\n\n", [error localizedDescription].UTF8String);
					exit(EXIT_FAILURE);
				}
			}];

			VZVirtioSocketListener *listener = [VZVirtioSocketListener new];
			socket_listener_delegate = [VZVirtioSocketListenerDelegateImpl new];
			[listener setDelegate:socket_listener_delegate];
			VZVirtioSocketDevice *socket_device = (VZVirtioSocketDevice *)vm.socketDevices.firstObject;
			[socket_device setSocketListener:listener forPort:VSOCK_PORT];
		});

		do {
			if (socket_fd != -1) {
				uint32_t return_code;
				ssize_t read_bytes = read(socket_fd, &return_code, sizeof(return_code));
				if (read_bytes < 0 || read_bytes != sizeof(return_code)) {
					fprintf(stderr, "\nError: Failed get exit code from guest: %zd, %s.\n\n", read_bytes, strerror(errno));
					exit(EXIT_FAILURE);
				}
				dispatch_sync(queue, ^{
					[vm stopWithCompletionHandler:^(NSError *error) {
						if (error) {
							fprintf(stderr, "\nError: VM failed to stop! \n\t%s\n\n", [error localizedDescription].UTF8String);
							exit(EXIT_FAILURE);
						}
					}];
				});
				return (int)return_code;
			}
			usleep(10000);
		} while (TRUE);
	}

	return EXIT_SUCCESS;
}
