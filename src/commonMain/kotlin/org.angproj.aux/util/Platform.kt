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


public object Platform {

    public val cpu: CPU by lazy { currentCpu() }
    public val bitness: BITNESS by lazy { currentBitness() }
    public val endian: ENDIAN by lazy { currentEndian() }
    public val libOs: LIB_OS by lazy { currentLibOs() }
    public val guiOs: GUI_OS by lazy { currentGuiOs() }
    public val variant: VARIANT by lazy { currentVariant() }


    public enum class ENDIAN {
        UNKNOWN,
        LITTLE_ENDIAN,
        BIG_ENDIAN;
    }

    public enum class BITNESS {
        UNKNOWN,
        SIZE_32BIT,
        SIZE_64BIT;
    }

    public enum class GUI_OS {
        UNKNOWN,
        MACOSX,
        IOS,
        LINUX,
        WINDOWS,
        ANDROID,
        WASM,
        TVOS,
        WATCHOS,
        X11,
        HEADLESS;
    }

    public enum class LIB_OS {
        UNKNOWN,
        DARWIN,
        FREEBSD,
        NETBSD,
        OPENBSD,
        DRAGONFLY,
        LINUX,
        SOLARIS,
        WINDOWS,
        AIX,
        IBMI,
        ZLINUX,
        MIDNIGHTBSD;

        public fun toLowerString(): String = this.name.lowercase()
    }

    public enum class CPU {
        UNKNOWN,
        I386, // 32 bit legacy Intel
        X86_64, // 64 bit AMD (aka EM64T/X64)
        PPC, // 32 bit Power PC
        PPC64, // 64 bit Power PC
        PPC64LE, // 64 bit Power PC little endian
        SPARC, // 32 bit Sun sparc
        SPARCV9, // 64 bit Sun sparc
        S390X, // IBM zSeries S/390
        MIPS32, // 32 bit MIPS (used by nestedvm)
        MIPS32EL,
        ARM32, // 32 bit ARM
        ARM64,
        AARCH64, // 64 bit ARM
        MIPS64EL, // 64 bit MIPS
        LOONGARCH64, // 64 bit LOONGARCH
        RISCV64; // 64 bit RISC-V

        public fun toLowerString(): String = this.name.lowercase()
    }

    public enum class VARIANT(public val code: String) {
        COMMON("Kotlin/Common"),
        NATIVE("Kotlin/Native"),
        JVM("Kotlin/Jvm"),
        JS("Kotlin/Js"),
        WASM("Kotlin/WasmJs"),
        WASI("Kotlin/WasmWasi");
    }


    public fun is32Bit(): Boolean = bitness == BITNESS.SIZE_32BIT

    public fun is64Bit(): Boolean = bitness == BITNESS.SIZE_64BIT

    public fun isBSD(): Boolean = libOs in bsd

    public fun isUnix(): Boolean = libOs !in notUnix

    public fun isLittleEndian(): Boolean = endian == ENDIAN.LITTLE_ENDIAN

    public fun isBigEndian(): Boolean = endian == ENDIAN.LITTLE_ENDIAN


    public val bsd: Set<LIB_OS> = setOf(
        LIB_OS.FREEBSD, LIB_OS.OPENBSD, LIB_OS.NETBSD,
        LIB_OS.DARWIN, LIB_OS.DRAGONFLY, LIB_OS.MIDNIGHTBSD
    )

    public val notUnix: Set<LIB_OS> = setOf(LIB_OS.WINDOWS)

    public val size32Bit: Set<CPU> = setOf(CPU.I386, CPU.PPC, CPU.SPARC)

    public val size64Bit: Set<CPU> = setOf(
        CPU.X86_64, CPU.PPC64, CPU.PPC64LE,
        CPU.SPARCV9, CPU.S390X, CPU.AARCH64,
        CPU.MIPS64EL, CPU.LOONGARCH64, CPU.RISCV64
    )
}

public expect fun Platform.currentVariant(): VARIANT

public expect fun Platform.currentBitness(): BITNESS

public expect fun Platform.currentEndian(): ENDIAN

public expect fun Platform.currentCpu(): CPU

public expect fun Platform.currentLibOs(): LIB_OS

public expect fun Platform.currentGuiOs(): GUI_OS
