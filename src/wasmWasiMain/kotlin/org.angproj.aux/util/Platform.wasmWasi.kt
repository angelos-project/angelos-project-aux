/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */

package org.angproj.aux.util

import org.angproj.aux.util.Platform.VARIANT
import org.angproj.aux.util.Platform.BITNESS
import org.angproj.aux.util.Platform.CPU
import org.angproj.aux.util.Platform.ENDIAN
import org.angproj.aux.util.Platform.LIB_OS
import org.angproj.aux.util.Platform.GUI_OS


public actual fun Platform.currentVariant(): VARIANT = VARIANT.WASI

public actual fun Platform.currentBitness(): BITNESS = when {
    else -> BITNESS.SIZE_32BIT // De facto
}

/**
 * Little-endian is standard
 * https://webassembly.org/docs/portability/
 * */
public actual fun Platform.currentEndian(): ENDIAN {
    return when {
        else -> ENDIAN.LITTLE_ENDIAN // De facto
    }
}

public actual fun Platform.currentCpu(): CPU = when {
    else -> CPU.UNKNOWN
}

public actual fun Platform.currentLibOs(): LIB_OS {
    return when {
        else -> LIB_OS.UNKNOWN
    }
}

public actual fun Platform.currentGuiOs(): GUI_OS {
    return when {
        else -> GUI_OS.UNKNOWN
    }
}
