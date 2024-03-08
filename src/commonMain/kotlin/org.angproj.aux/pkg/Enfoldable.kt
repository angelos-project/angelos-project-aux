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

import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable

public interface Enfoldable {

    public val foldFormat: FoldFormat

    public fun foldSize(foldFormat: FoldFormat): Long

    public fun enfoldBlock(outData: Storable, offset: Int): Long { throw UnsupportedOperationException() }

    public fun enfoldStream(outStream: Writable): Long { throw UnsupportedOperationException() }

    public companion object {
        public const val TYPE_SIZE: Long = 2
        public const val COUNT_SIZE: Long = 4
        public const val LENGTH_SIZE: Long = 8
        public const val END_SIZE: Long = 1

        public const val OVERHEAD_BASIC: Long = TYPE_SIZE + END_SIZE
        public const val OVERHEAD_LENGTH: Long = TYPE_SIZE + LENGTH_SIZE + END_SIZE
        public const val OVERHEAD_COUNT: Long = TYPE_SIZE + COUNT_SIZE + END_SIZE

        public fun setType(outStream: Writable, type: Convention) { outStream.writeShort(type.type) }

        public fun setCount(outStream: Writable, count: Int) { outStream.writeInt(count) }

        public fun setCount(outData: Storable, offset: Int, count: Int) { outData.storeInt(offset, count) }

        public fun setLength(outStream: Writable, length: Long) { outStream.writeLong(length) }

        public fun setLength(outData: Storable, offset: Int, length: Long) { outData.storeLong(offset, length) }

        public fun setEnd(outStream: Writable, end: Convention) { outStream.writeByte(end.end) }
    }
}