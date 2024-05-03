/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.buf

import org.angproj.aux.io.TypeSize
import kotlin.jvm.JvmStatic

/**
 * The purpose of SpeedCopy is to let implemented buffers or arrays be able to copy its data to a new one
 * with the speed of 64-bit chunks, speeding up if any variable type is of a different kind.
 * All implementing classes should therefore reserve a size that is divisible of 64 bits or 8 bytes long.
 * */
public interface SpeedCopy {
    public val size: Int
    public val length: Int
    public val marginSized: Int

    public companion object {
        @JvmStatic
        public fun addMarginInTotalBytes(indexCount: Int, typeSize: TypeSize): Int {
            require(indexCount >= 0)

            val byteCount = indexCount * typeSize.size
            val remainder = byteCount % TypeSize.LONG.size
            return (byteCount + if(remainder == 0) 0 else TypeSize.LONG.size - remainder)
        }

        /**
         * The xCnt variable sets the amount of x indices in an array-like buffer if x is the variable type used.
         * The xLen2addMargin adds enough of invisible indices to equal a divisible size of type Long, described in
         * type x.
         * */
        @JvmStatic
        public fun addMarginByIndexType(indexCount: Int, typeSize: TypeSize): Int {
            require(indexCount >= 0)

            val factor = TypeSize.LONG.size / typeSize.size
            val remainder = indexCount % factor
            return (indexCount + if(remainder == 0) 0 else factor - remainder)
        }
    }
}