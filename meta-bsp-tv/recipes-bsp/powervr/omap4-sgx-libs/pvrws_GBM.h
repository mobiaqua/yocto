/****************************************************************************
**
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser General Public
** License version 2.1 as published by the Free Software Foundation and
** appearing in the file LICENSE.LGPL included in the packaging of this
** file. Please review the following information to ensure the GNU Lesser
** General Public License version 2.1 requirements will be met:
** http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU General
** Public License version 3.0 as published by the Free Software Foundation
** and appearing in the file LICENSE.GPL included in the packaging of this
** file. Please review the following information to ensure the GNU General
** Public License version 3.0 requirements will be met:
** http://www.gnu.org/copyleft/gpl.html.
**
****************************************************************************/

#ifndef _PVR_GBM_H_
#define _PVR_GBM_H_

#include "gbm_pvrint.h"

typedef struct gbm_device     *NativeDisplayType;
typedef struct gbm_surface    *NativeWindowType;
typedef struct gbm_bo         *NativePixmapType;

#include <wsegl/wsegl.h>
#include <pvr2d/pvr2d.h>

typedef enum _GBMDrawableType
{
    GBM_DRAWABLE_UNKNOWN,
    GBM_DRAWABLE_WINDOW,
    GBM_DRAWABLE_PIXMAP,
} GBMDrawableType;

struct GBMDisplay
{
    PVR2DCONTEXTHANDLE  pvr2dContextHandle;
};

struct GBMBuffer
{
    PVR2DMEMINFO        *pvr2dMemInfo;
};

struct GBMDrawable
{
    GBMDrawableType     type;
    int                 width;
    int                 height;
    int                 stride;
    union gbp_pvr_handle
    {
        struct gbm_pvr_surface *window;
        struct gbm_pvr_bo      *pixmap;
    } handle;
    struct GBMDisplay   *display;
    int                 numBackBuffers;
    struct GBMBuffer    *backBuffers;
};

#endif
