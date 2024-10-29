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

import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.TypeSize

public interface Enfoldable {

    public fun foldSize(foldFormat: FoldFormat): Int

    public fun enfoldBlock(outData: Storable, offset: Int): Int {
        throw UnsupportedOperationException()
    }

    public fun enfoldStream(outStream: BinaryWritable): Int {
        throw UnsupportedOperationException()
    }

    public companion object {
        public const val TYPE_SIZE: Int = TypeSize.short
        public const val CONTENT_SIZE: Int = TypeSize.byte
        public const val COUNT_SIZE: Int = TypeSize.int
        public const val LENGTH_SIZE: Int = TypeSize.long
        public const val END_SIZE: Int = TypeSize.byte


        public const val OVERHEAD_BASIC: Int = TYPE_SIZE + END_SIZE
        public const val OVERHEAD_LENGTH: Int = LENGTH_SIZE + OVERHEAD_BASIC
        public const val OVERHEAD_COUNT: Int = COUNT_SIZE + OVERHEAD_BASIC
        public const val OVERHEAD_CONTENT: Int = CONTENT_SIZE + OVERHEAD_COUNT


        public fun setType(outStream: BinaryWritable, type: Convention) {
            outStream.writeShort(type.type)
        }

        public fun setContent(outStream: BinaryWritable, content: Byte) {
            outStream.writeByte(content)
        }

        public fun setContent(outData: Storable, offset: Int, content: Byte) {
            outData.storeByte(offset, content)
        }

        public fun setCount(outStream: BinaryWritable, count: Int) {
            outStream.writeInt(count)
        }

        public fun setCount(outData: Storable, offset: Int, count: Int) {
            outData.storeInt(offset, count)
        }

        public fun setLength(outStream: BinaryWritable, length: Long) {
            outStream.writeLong(length)
        }

        public fun setLength(outData: Storable, offset: Int, length: Long) {
            outData.storeLong(offset, length)
        }

        public fun setEnd(outStream: BinaryWritable, end: Convention) {
            outStream.writeByte(end.end)
        }
    }
}