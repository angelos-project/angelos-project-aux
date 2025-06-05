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
import kotlin.experimental.ExperimentalNativeApi


public actual fun Platform.currentVariant(): VARIANT = VARIANT.NATIVE

@OptIn(ExperimentalNativeApi::class)
public actual fun Platform.currentBitness(): BITNESS = when(kotlin.native.Platform.cpuArchitecture.bitness) {
    64 -> BITNESS.SIZE_64BIT
    32 -> BITNESS.SIZE_32BIT
    else -> BITNESS.UNKNOWN
}

@OptIn(ExperimentalNativeApi::class)
public actual fun Platform.currentEndian(): ENDIAN = when(kotlin.native.Platform.isLittleEndian) {
    true -> ENDIAN.LITTLE_ENDIAN
    else -> ENDIAN.BIG_ENDIAN
}

@OptIn(ExperimentalNativeApi::class)
public actual fun Platform.currentCpu(): CPU = when(kotlin.native.Platform.cpuArchitecture) {
    CpuArchitecture.X64 -> CPU.X86_64
    CpuArchitecture.X86 -> CPU.I386
    CpuArchitecture.ARM32 -> CPU.ARM32
    CpuArchitecture.ARM64 -> CPU.ARM64
    CpuArchitecture.MIPS32 -> CPU.MIPS32
    CpuArchitecture.MIPSEL32 -> CPU.MIPS32EL
    //CpuArchitecture.WASM32 ->
    else -> CPU.UNKNOWN
}

@OptIn(ExperimentalNativeApi::class)
public actual fun Platform.currentLibOs(): LIB_OS {
    return when(kotlin.native.Platform.osFamily) {
        OsFamily.MACOSX -> LIB_OS.DARWIN
        OsFamily.IOS -> LIB_OS.DARWIN
        OsFamily.TVOS -> LIB_OS.DARWIN
        OsFamily.WATCHOS -> LIB_OS.DARWIN
        OsFamily.ANDROID -> LIB_OS.LINUX
        OsFamily.LINUX -> LIB_OS.LINUX
        OsFamily.WINDOWS -> LIB_OS.WINDOWS
        //OsFamily.WASM -> LIB_OS.UNKNOWN
        else -> LIB_OS.UNKNOWN
    }
}

@OptIn(ExperimentalNativeApi::class)
public actual fun Platform.currentGuiOs(): GUI_OS {
    return when(kotlin.native.Platform.osFamily) {
        OsFamily.MACOSX -> GUI_OS.MACOSX
        OsFamily.IOS -> GUI_OS.IOS
        OsFamily.TVOS -> GUI_OS.TVOS
        OsFamily.WATCHOS -> GUI_OS.WATCHOS
        OsFamily.ANDROID -> GUI_OS.ANDROID
        OsFamily.LINUX -> GUI_OS.LINUX
        OsFamily.WINDOWS -> GUI_OS.WINDOWS
        else -> GUI_OS.UNKNOWN
    }
}
