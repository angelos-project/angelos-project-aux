/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

public interface EndianAware {

    public fun Short.asBig(): Short = if(bigEndian) this else this.swapEndian()
    public fun UShort.asBig(): UShort = if(bigEndian) this else this.swapEndian()
    public fun Char.asBig(): Char = if(bigEndian) this else this.swapEndian()

    public fun Int.asBig(): Int = if(bigEndian) this else this.swapEndian()
    public fun UInt.asBig(): UInt = if(bigEndian) this else this.swapEndian()
    public fun Long.asBig(): Long = if(bigEndian) this else this.swapEndian()
    public fun ULong.asBig(): ULong = if(bigEndian) this else this.swapEndian()

    public fun Float.asBig(): Float = if(bigEndian) this else this.swapEndian()
    public fun Double.asBig(): Double = if(bigEndian) this else this.swapEndian()

    public companion object {
        public val bigEndian: Boolean = !Endian.native.asLittleIfUnknown()

        public fun Short.asBig(): Short = if(bigEndian) this else this.swapEndian()
        public fun UShort.asBig(): UShort = if(bigEndian) this else this.swapEndian()
        public fun Char.asBig(): Char = if(bigEndian) this else this.swapEndian()

        public fun Int.asBig(): Int = if(bigEndian) this else this.swapEndian()
        public fun UInt.asBig(): UInt = if(bigEndian) this else this.swapEndian()
        public fun Long.asBig(): Long = if(bigEndian) this else this.swapEndian()
        public fun ULong.asBig(): ULong = if(bigEndian) this else this.swapEndian()

        public fun Float.asBig(): Float = if(bigEndian) this else this.swapEndian()
        public fun Double.asBig(): Double = if(bigEndian) this else this.swapEndian()
    }
}