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
#include <string.h>

int main(int argc, char **argv)
{
    if (argc != 3)
        return 100;

    char *cmd_str = strstr(argv[1], argv[2]);
    if (!cmd_str)
        return 101;

    char *cmd_arg = cmd_str + strlen(argv[2]) + 1;

    char *p = cmd_arg;
    int open = 0;
    do
    {
        if (!open && *p == ' ')
        {
            *p = '\0';
        }
        else if (*p == '"')
        {
            open = !open;
        }
    } while (*p++ != '\0');

    int found;
    do
    {
        p = cmd_arg;
        found = 0;
        do
        {
            if (!found && *p == '"')
                found = 1;
            if (found)
                *p = *(p + 1);
        } while (*p++ != '\0');
    } while (found);

    printf("%s", cmd_arg);

    return 0;
}
