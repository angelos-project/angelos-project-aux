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

    public companion object {
        /**
         * The xCnt variable sets the amount of x indices in an array-like buffer if x is the variable type used.
         * The xLen2addMargin adds enough of invisible indices to equal a divisible sie of type Long, described in
         * type x.
         * */
        @JvmStatic
        public fun addMargin(count: Int, typeSize: TypeSize): Int {
            require(count >= 0)

            val byteCnt = count * typeSize.size
            val rem = byteCnt % TypeSize.LONG.size
            return (byteCnt / TypeSize.LONG.size + if(rem == 0) 0 else TypeSize.LONG.size - rem) / typeSize.size
        }
    }
}

public inline fun <reified E: SpeedCopy> SpeedCopy.copyOfRange(fromIdx: Int, toIdx: Int): E {
    TODO("Not yet implemented")
}

public inline fun <reified E: SpeedCopy> SpeedCopy.copyOf(): E {
    TODO("Not yet implemented")
}