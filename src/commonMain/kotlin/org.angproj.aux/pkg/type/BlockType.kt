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
package org.angproj.aux.pkg.type

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import org.angproj.aux.util.*
import kotlin.jvm.JvmInline

@JvmInline
public value class BlockType(public val block: ByteArray) : Storable, Retrievable, Enfoldable {

    public constructor(size: Long) : this(ByteArray(size.toInt()))

    override fun retrieveByte(position: Int): Byte = block[position]

    override fun retrieveUByte(position: Int): UByte = block[position].toUByte()

    override fun retrieveChar(position: Int): Char = block.readCharAt(position)

    override fun retrieveShort(position: Int): Short = block.readShortAt(position)

    override fun retrieveUShort(position: Int): UShort = block.readUShortAt(position)

    override fun retrieveInt(position: Int): Int = block.readIntAt(position)

    override fun retrieveUInt(position: Int): UInt = block.readUIntAt(position)

    override fun retrieveLong(position: Int): Long = block.readLongAt(position)

    override fun retrieveULong(position: Int): ULong = block.readULongAt(position)

    override fun retrieveFloat(position: Int): Float = block.readFloatAt(position)

    override fun retrieveDouble(position: Int): Double = block.readDoubleAt(position)

    override fun storeByte(position: Int, value: Byte) {
        block[position] = value
    }

    override fun storeUByte(position: Int, value: UByte) {
        block[position] = value.toByte()
    }

    override fun storeChar(position: Int, value: Char) { block.writeCharAt(position, value) }

    override fun storeShort(position: Int, value: Short) { block.writeShortAt(position, value) }

    override fun storeUShort(position: Int, value: UShort) { block.writeUShortAt(position, value) }

    override fun storeInt(position: Int, value: Int) { block.writeIntAt(position, value) }

    override fun storeUInt(position: Int, value: UInt) { block.writeUIntAt(position, value) }

    override fun storeLong(position: Int, value: Long) { block.writeLongAt(position, value) }

    override fun storeULong(position: Int, value: ULong) { block.writeULongAt(position, value) }

    override fun storeFloat(position: Int, value: Float) { block.writeFloatAt(position, value) }

    override fun storeDouble(position: Int, value: Double) { block.writeDoubleAt(position, value) }

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when(foldFormat) {
        FoldFormat.BLOCK -> block.size.toLong()
        FoldFormat.STREAM -> block.size + Enfoldable.OVERHEAD_LENGTH
    }

    protected fun chunk(index: Int, length: Int, slice: Int, action: (Int) -> Unit): Int {
        val steps = (length - index) / slice
        val size = steps * slice
        if(steps > 0) (index until (index + size) step slice).forEach { action(it) }
        return index + size
    }

    public fun enfoldToBlock(outData: Storable, offset: Int): Long {
        var index = chunk(0, block.size, Long.SIZE_BYTES) {
            outData.storeLong(offset + it, block.readLongAt(it)) }

        /*var index = 0
        (index until block.size step Long.SIZE_BYTES).forEach {
            index = it
            outData.storeLong(offset + index, block.readLongAt(index))
        }
        (index until block.size step Int.SIZE_BYTES).forEach {
            index = it
            outData.storeInt(offset + index, block.readIntAt(index))
        }
        (index until block.size step Short.SIZE_BYTES).forEach {
            index = it
            outData.storeShort(offset + index, block.readShortAt(index))
        }
        (index until block.size step Byte.SIZE_BYTES).forEach {
            index = it
            outData.storeByte(offset + index, block[index])
        }*/
        return foldSize(FoldFormat.BLOCK)
    }

    public fun enfoldToStreamByConvention(outStream: Writable, type: Convention): Long {
        Enfoldable.setType(outStream, type)
        Enfoldable.setLength(outStream, foldSize(FoldFormat.STREAM) - Enfoldable.OVERHEAD_LENGTH)
        var index = 0
        (index until block.size step Long.SIZE_BYTES).forEach {
            index = it
            outStream.writeLong(block.readLongAt(index))
        }
        (index until block.size step Int.SIZE_BYTES).forEach {
            index = it
            outStream.writeInt(block.readIntAt(index))
        }
        (index until block.size step Short.SIZE_BYTES).forEach {
            index = it
            outStream.writeShort(block.readShortAt(index))
        }
        (index until block.size step Byte.SIZE_BYTES).forEach {
            index = it
            outStream.writeByte(block[index])
        }
        Enfoldable.setEnd(outStream, type)
        return foldSize(FoldFormat.STREAM)
    }

    public fun enfoldToStream(outStream: Writable): Long = enfoldToStreamByConvention(outStream, conventionType)

    public companion object: Unfoldable<BlockType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.BLOCK
        override val atomicSize: Int = 0

        public fun unfoldFromBlock(inData: Retrievable, offset: Int, length: Long) : BlockType {
            require(length <= Int.MAX_VALUE)
            val block = BlockType(length)
            var index = 0
            (index until block.block.size step Long.SIZE_BYTES).forEach {
                index = it
                block.storeLong(index, inData.retrieveLong(offset + index))
            }
            (index until block.block.size step Int.SIZE_BYTES).forEach {
                index = it
                block.storeInt(index, inData.retrieveInt(offset + index))
            }
            (index until block.block.size step Short.SIZE_BYTES).forEach {
                index = it
                block.storeShort(index, inData.retrieveShort(offset + index))
            }
            (index until block.block.size step Byte.SIZE_BYTES).forEach {
                index = it
                block.storeByte(index, inData.retrieveByte(offset + index))
            }
            return block
        }

        public fun unfoldFromStreamByConvention(inStream: Readable, type: Convention) : BlockType {
            require(Unfoldable.getType(inStream, type))
            val length = Unfoldable.getLength(inStream)
            require(length <= Int.MAX_VALUE)
            val block = BlockType(length)
            var index = 0
            (index until block.block.size step Long.SIZE_BYTES).forEach {
                index = it
                block.storeLong(index, inStream.readLong())
            }
            (index until block.block.size step Int.SIZE_BYTES).forEach {
                index = it
                block.storeInt(index, inStream.readInt())
            }
            (index until block.block.size step Short.SIZE_BYTES).forEach {
                index = it
                block.storeShort(index, inStream.readShort())
            }
            (index until block.block.size step Byte.SIZE_BYTES).forEach {
                index = it
                block.storeByte(index, inStream.readByte())
            }
            require(Unfoldable.getEnd(inStream, type))
            return block
        }

        public fun unfoldFromStream(inStream: Readable) : BlockType = unfoldFromStreamByConvention(inStream, conventionType)
    }
}