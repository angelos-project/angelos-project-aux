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
    public companion object {
        public val bigEndian: Boolean = !Endian.native.asLittleIfUnknown()

        public inline fun Short.asBig(): Short = if(bigEndian) this else this.swapEndian()
        public inline fun UShort.asBig(): UShort = if(bigEndian) this else this.swapEndian()
        public inline fun Char.asBig(): Char = if(bigEndian) this else this.swapEndian()

        public inline fun Int.asBig(): Int = if(bigEndian) this else this.swapEndian()
        public inline fun UInt.asBig(): UInt = if(bigEndian) this else this.swapEndian()
        public inline fun Long.asBig(): Long = if(bigEndian) this else this.swapEndian()
        public inline fun ULong.asBig(): ULong = if(bigEndian) this else this.swapEndian()

        public inline fun Float.asBig(): Float = if(bigEndian) this else this.swapEndian()
        public inline fun Double.asBig(): Double = if(bigEndian) this else this.swapEndian()
    }
}