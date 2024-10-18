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
package org.angproj.aux.pkg

import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.pkg.arb.StructType

public interface Unfoldable<E : Enfoldable> {
    public val foldFormatSupport: List<FoldFormat>

    public val conventionType: Convention

    public val atomicSize: Int

    public fun isFoldFormatSupported(format: FoldFormat): Boolean = foldFormatSupport.contains(format)

    public fun unfoldBlock(inData: Retrievable, offset: Int): E {
        throw UnsupportedOperationException()
    }

    public fun unfoldBlock(inData: Retrievable, offset: Int, size: Int): E {
        throw UnsupportedOperationException()
    }

    public fun unfoldStream(inStream: BinaryReadable): E {
        throw UnsupportedOperationException()
    }

    public fun <P : Packageable> unfold(
        inData: Retrievable, offset: Int, unpack: (Retrievable, Int) -> P
    ): StructType<P> {
        throw UnsupportedOperationException()
    }

    public companion object {

        public fun getType(inStream: BinaryReadable, type: Convention): Boolean = inStream.readShort() == type.type

        public fun getContent(inStream: BinaryReadable): Byte = inStream.readByte()

        public fun getCount(inStream: BinaryReadable): Int = inStream.readInt()

        public fun getLength(inStream: BinaryReadable): Long = inStream.readLong()

        public fun getEnd(inStream: BinaryReadable, end: Convention): Boolean = inStream.readByte() == end.end
    }
}