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

#include <stdio.h>
#include <string.h>
#include <dlfcn.h>

typedef int SrvInitFunc(void);

static SrvInitFunc *SrvInit;

int main(int argc, char **argv)
{
    int status;
    void *handle;

    handle = dlopen("/usr/lib/libsrv_init.so", RTLD_NOW | RTLD_GLOBAL);
    if (!handle) {
        fprintf(stderr, "Failed to load SGX 'srv_init' library: %s\n", dlerror());
        return 1;
    }

    SrvInit = dlsym(handle, "SrvInit");
    if (!SrvInit) {
        fprintf(stderr, "Failed to find symbol: %s\n", dlerror());
        dlclose(handle);
        return 1;
    }

    status = SrvInit();
    if (status)
    {
        fprintf(stderr, "SrvInit: Failed to init SGX services, error code: %d\n", status);
        dlclose(handle);
        return 1;
    }

    dlclose(handle);
    return 0;
}
