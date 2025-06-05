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


public actual fun Platform.currentVariant(): VARIANT = VARIANT.JVM

public actual fun Platform.currentBitness(): BITNESS = when(cpu) {
    in size64Bit -> BITNESS.SIZE_64BIT
    in size32Bit -> BITNESS.SIZE_32BIT
    else -> BITNESS.UNKNOWN
}

public actual fun Platform.currentEndian(): ENDIAN = when(System.getProperty("sun.cpu.endian").lowercase()) {
    "little" -> ENDIAN.LITTLE_ENDIAN
    "big" -> ENDIAN.BIG_ENDIAN
    else -> ENDIAN.UNKNOWN
}

public actual fun Platform.currentCpu(): CPU = when(System.getProperty("os.arch").lowercase()) {
    "x86", "i386", "i86pc", "i686" -> CPU.I386
    "x86_64", "amd64" -> CPU.X86_64
    "ppc", "powerpc" -> when(libOs) {
        LIB_OS.IBMI -> CPU.PPC64
        else -> CPU.PPC
    }
    "ppc64", "powerpc64" -> when(endian) {
        ENDIAN.LITTLE_ENDIAN -> CPU.PPC64LE
        else -> CPU.PPC64
    }
    "ppc64le", "powerpc64le" -> CPU.PPC64LE
    "s390", "s390x" -> CPU.S390X
    "aarch64" -> CPU.AARCH64
    "arm","armv7l" -> when(bitness) {
        BITNESS.SIZE_32BIT -> CPU.ARM32
        else -> CPU.ARM64
    }
    "mips64", "mips64el" -> CPU.MIPS64EL
    "loongarch64" -> CPU.LOONGARCH64
    "riscv64" -> CPU.RISCV64
    else -> CPU.UNKNOWN
}

public actual fun Platform.currentLibOs(): LIB_OS {
    val osName = System.getProperty("os.name").split(" ".toRegex()).dropLastWhile {
        it.isEmpty() }.toTypedArray()[0].lowercase()

    return when {
        osName.startsWith("mac", true) -> LIB_OS.DARWIN
        osName.startsWith("darwin", true) -> LIB_OS.DARWIN
        osName.startsWith("linux", true) -> LIB_OS.LINUX
        osName.startsWith("sunos", true) -> LIB_OS.SOLARIS
        osName.startsWith("solaris", true) -> LIB_OS.SOLARIS
        osName.startsWith("aix", true) -> LIB_OS.AIX
        osName.startsWith("os400", true) -> LIB_OS.IBMI
        osName.startsWith("os/400", true) ->  LIB_OS.IBMI
        osName.startsWith("openbsd", true) -> LIB_OS.OPENBSD
        osName.startsWith("freebsd", true) -> LIB_OS.FREEBSD
        osName.startsWith("dragonfly", true) -> LIB_OS.DRAGONFLY
        osName.startsWith("windows", true) -> LIB_OS.WINDOWS
        osName.startsWith("midnightbsd", true) -> LIB_OS.MIDNIGHTBSD
        else -> LIB_OS.UNKNOWN
    }
}

public actual fun Platform.currentGuiOs(): GUI_OS {
    val osName = System.getProperty("os.name").split(" ".toRegex()).dropLastWhile {
        it.isEmpty() }.toTypedArray()[0].lowercase()

    return when {
        osName.startsWith("mac", true) -> GUI_OS.MACOSX
        osName.startsWith("darwin", true) -> GUI_OS.MACOSX
        osName.startsWith("linux", true) -> GUI_OS.LINUX
        osName.startsWith("sunos", true) -> GUI_OS.X11
        osName.startsWith("solaris", true) -> GUI_OS.X11
        osName.startsWith("aix", true) -> GUI_OS.HEADLESS
        osName.startsWith("os400", true) -> GUI_OS.HEADLESS
        osName.startsWith("os/400", true) ->  GUI_OS.HEADLESS
        osName.startsWith("openbsd", true) -> GUI_OS.HEADLESS
        osName.startsWith("freebsd", true) -> GUI_OS.X11
        osName.startsWith("dragonfly", true) -> GUI_OS.X11
        osName.startsWith("windows", true) -> GUI_OS.WINDOWS
        osName.startsWith("midnightbsd", true) -> GUI_OS.X11
        else -> GUI_OS.UNKNOWN
    }
}
