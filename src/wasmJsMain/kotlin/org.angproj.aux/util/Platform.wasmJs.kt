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

import org.khronos.webgl.*
import org.angproj.aux.util.Platform.VARIANT
import org.angproj.aux.util.Platform.BITNESS
import org.angproj.aux.util.Platform.CPU
import org.angproj.aux.util.Platform.ENDIAN
import org.angproj.aux.util.Platform.LIB_OS
import org.angproj.aux.util.Platform.GUI_OS


public actual fun Platform.currentVariant(): VARIANT = VARIANT.WASM

public actual fun Platform.currentBitness(): BITNESS = when {
    else -> BITNESS.SIZE_32BIT // De facto
}

/**
 * Little-endian is standard
 * https://webassembly.org/docs/portability/
 * */
/**
 * Little/big endian test from:
 * https://developers.redhat.com/articles/2021/12/09/how-nodejs-uses-v8-javascript-engine-run-your-code#big_endian_byte_order_on_v8
 */
public actual fun Platform.currentEndian(): ENDIAN {
    /*val buffer = ArrayBuffer(16)
    val int8View = Int8Array(buffer)
    val int16View = Int16Array(buffer)
    int16View[0] = 5

    return when (int8View[0].toInt()) {
        5 -> ENDIAN.LITTLE_ENDIAN
        else -> ENDIAN.BIG_ENDIAN
    }*/
    return ENDIAN.LITTLE_ENDIAN
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
