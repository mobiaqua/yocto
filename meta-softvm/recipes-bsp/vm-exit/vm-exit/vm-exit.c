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

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <linux/vm_sockets.h>
#include <netinet/in.h>

#define VSOCK_PORT 6666

int main(int argc, char **argv)
{
    int error_code, fd;
    struct sockaddr_vm addr;
    char buffer[4];

    if (argc == 1)
    {
        perror("Missing argument");
        return EXIT_FAILURE;
    }
    error_code = atoi(argv[1]);

    if ((fd = socket(AF_VSOCK, SOCK_STREAM, 0)) < 0)
    {
        perror("Failed open virtual socket");
        return EXIT_FAILURE;
    }

    memset(&addr, 0, sizeof(struct sockaddr_vm));
    addr.svm_family = AF_VSOCK;
    addr.svm_port = VSOCK_PORT;
    addr.svm_cid = VMADDR_CID_HOST;

    if (connect(fd, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    {
        perror("Failed connect to virtual socket");
        close(fd);
        return EXIT_FAILURE;
    }

    ssize_t send = write(fd, &error_code, sizeof(error_code));
    if (send < 0 || send != sizeof(error_code))
    {
        perror("Failed send data");
        close(fd);
        return EXIT_FAILURE;
    }

    close (fd);

    for (;;) { sleep(1); }
}
