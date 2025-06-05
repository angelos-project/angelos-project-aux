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

import org.angproj.aux.io.*
import org.angproj.aux.pkg.*
import org.angproj.aux.util.floorMod
import kotlin.jvm.JvmInline

@JvmInline
public value class BlockType(public val block: Binary) : Storable, Retrievable, Enfoldable {

    override fun retrieveByte(position: Int): Byte = block.retrieveByte(position)
    override fun retrieveUByte(position: Int): UByte = block.retrieveUByte(position)
    override fun retrieveShort(position: Int): Short = block.retrieveShort(position)
    override fun retrieveUShort(position: Int): UShort = block.retrieveUShort(position)
    override fun retrieveInt(position: Int): Int = block.retrieveInt(position)
    override fun retrieveUInt(position: Int): UInt = block.retrieveUInt(position)
    override fun retrieveLong(position: Int): Long = block.retrieveLong(position)
    override fun retrieveULong(position: Int): ULong = block.retrieveULong(position)
    override fun retrieveFloat(position: Int): Float = block.retrieveFloat(position)
    override fun retrieveDouble(position: Int): Double = block.retrieveDouble(position)

    override fun retrieveRevShort(position: Int): Short = block.retrieveRevShort(position)
    override fun retrieveRevUShort(position: Int): UShort = block.retrieveRevUShort(position)
    override fun retrieveRevInt(position: Int): Int = block.retrieveRevInt(position)
    override fun retrieveRevUInt(position: Int): UInt = block.retrieveRevUInt(position)
    override fun retrieveRevLong(position: Int): Long = block.retrieveRevLong(position)
    override fun retrieveRevULong(position: Int): ULong = block.retrieveRevULong(position)
    override fun retrieveRevFloat(position: Int): Float = block.retrieveRevFloat(position)
    override fun retrieveRevDouble(position: Int): Double = block.retrieveRevDouble(position)


    override fun storeByte(position: Int, value: Byte): Unit = block.storeByte(position, value)
    override fun storeUByte(position: Int, value: UByte): Unit = block.storeUByte(position, value)
    override fun storeShort(position: Int, value: Short): Unit = block.storeShort(position, value)
    override fun storeUShort(position: Int, value: UShort): Unit = block.storeUShort(position, value)
    override fun storeInt(position: Int, value: Int): Unit = block.storeInt(position, value)
    override fun storeUInt(position: Int, value: UInt): Unit = block.storeUInt(position, value)
    override fun storeLong(position: Int, value: Long): Unit = block.storeLong(position, value)
    override fun storeULong(position: Int, value: ULong): Unit = block.storeULong(position, value)
    override fun storeFloat(position: Int, value: Float): Unit = block.storeFloat(position, value)
    override fun storeDouble(position: Int, value: Double): Unit = block.storeDouble(position, value)

    override fun storeRevShort(position: Int, value: Short): Unit = block.storeRevShort(position, value)
    override fun storeRevUShort(position: Int, value: UShort): Unit = block.storeRevUShort(position, value)
    override fun storeRevInt(position: Int, value: Int): Unit = block.storeRevInt(position, value)
    override fun storeRevUInt(position: Int, value: UInt): Unit = block.storeRevUInt(position, value)
    override fun storeRevLong(position: Int, value: Long): Unit = block.storeRevLong(position, value)
    override fun storeRevULong(position: Int, value: ULong): Unit = block.storeRevULong(position, value)
    override fun storeRevFloat(position: Int, value: Float): Unit = block.storeRevFloat(position, value)
    override fun storeRevDouble(position: Int, value: Double): Unit = block.storeRevDouble(position, value)

    override fun foldSize(foldFormat: FoldFormat): Int = when (foldFormat) {
        FoldFormat.BLOCK -> block.limit
        FoldFormat.STREAM -> block.limit + Enfoldable.OVERHEAD_LENGTH
    }

    public fun enfoldToBlock(outData: Storable, offset: Int): Int = with(block) {
        val index = chunkLoop<Unit>(0, limit, TypeSize.long) { outData.storeLong(offset + it, retrieveLong(it)) }
        chunkLoop<Unit>(index, limit, TypeSize.byte) { outData.storeByte(offset + it, retrieveByte(it)) }
        //val index = longLoop<Unit>(limit) { outData.storeLong(offset + it, retrieveLong(it)) }
        //byteLoop<Unit>(index, limit) { outData.storeByte(offset + it, retrieveByte(it)) }
    }

    public fun enfoldToStreamByConvention(outStream: BinaryWritable, type: Convention): Int = outStream.measureBytes {
        Enfoldable.setType(outStream, type)
        Enfoldable.setLength(outStream, foldSize(FoldFormat.STREAM) - Enfoldable.OVERHEAD_LENGTH) // FoldFormat.BLOCK ?
        enfoldToStreamRaw(outStream, block)
        Enfoldable.setEnd(outStream, type)
    }.toInt()

    /**
     * Allows for packaging arbitrary data blocks which uses hashing that can be verified, also a 64-bit key
     * can be used to XOR the checksum, should give decent protection against forceful corruption attempts of
     * signed data. However, not cryptographically proven yet.
     * */
    public fun enfoldToStreamByCheck(outStream: BinaryWritable, key: Long = 0): Int = outStream.measureBytes {
        Enfoldable.setType(outStream, Convention.CHECK)
        Enfoldable.setLength(outStream, foldSize(FoldFormat.BLOCK)) // FoldFormat.BLOCK ?
        Enfoldable.setCheck(outStream, block.checkSum(key))
        enfoldToStreamRaw(outStream, block)
        Enfoldable.setEnd(outStream, Convention.CHECK)
    }.toInt()

    public fun enfoldToStream(outStream: BinaryWritable): Int = enfoldToStreamByConvention(outStream, conventionType)

    public companion object : Unfoldable<BlockType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.BLOCK
        override val atomicSize: Int = 0

        private inline fun <reified E: Any> longLoop(length: Int, action: (Int) -> Unit): Int {
            val size = length - length.floorMod(TypeSize.long)
            var idx = 0
            while(idx < size) {
                action(idx)
                idx += TypeSize.long
            }
            return idx
        }

        private inline fun <reified E: Any> byteLoop(index: Int, length: Int, action: (Int) -> Unit): Int {
            var idx = index
            while(idx < length) {
                action(idx)
                idx++
            }
            return idx
        }

        private inline fun <reified E: Any> chunkLoop(index: Int, length: Int, slice: Int, action: (Int) -> Unit): Int {
            val steps = (length - index) / slice
            val size = steps * slice
            if (steps > 0) (index until (index + size) step slice).forEach { action(it) }
            return index + size
        }

        public fun unfoldFromBlock(inData: Retrievable, offset: Int, block: Binary): Int = with(block) {
            val index = chunkLoop<Unit>(0, limit, TypeSize.long) { storeLong(it, inData.retrieveLong(offset + it)) }
            chunkLoop<Unit>(index, limit, TypeSize.byte) { storeByte(it, inData.retrieveByte(offset + it)) }
            //val index = longLoop<Unit>(limit) { storeLong(it, inData.retrieveLong(offset + it)) }
            //byteLoop<Unit>(index, limit) { storeByte(it, inData.retrieveByte(offset + it)) }
        }

        public fun unfoldFromBlock(inData: Retrievable, offset: Int, length: Int): BlockType {
            require(length <= DataSize._1G.size)
            val block = BlockType(binOf(length))
            unfoldFromBlock(inData, offset, block.block)
            return block
        }

        /*public fun unfoldFromStreamByConvention(inStream: BinaryReadable, type: Convention): BlockType {
            require(Unfoldable.getType(inStream, type))
            val length = Unfoldable.getLength(inStream).toInt()
            require(length <= Int.MAX_VALUE)
            val block = BlockType(binOf(length))
            var index = chunkLoop(0, length, TypeSize.long) { block.storeLong(it, inStream.readLong()) }
            index = chunkLoop(index, length, TypeSize.byte) { block.storeByte(it, inStream.readByte()) }
            require(Unfoldable.getEnd(inStream, type))
            return block
        }*/

        public fun unfoldFromStreamByConvention(inStream: BinaryReadable, type: Convention): BlockType {
            require(Unfoldable.getType(inStream, type))
            val block = inStream.measureBytes(Unfoldable.getLength(inStream)) {
                require(this@measureBytes <= Int.MAX_VALUE)
                BlockType(binOf(this@measureBytes.toInt())).apply {
                    unfoldFromStreamRaw(inStream, this@apply.block)
                }
            }
            require(Unfoldable.getEnd(inStream, type))
            return block
        }

        public fun enfoldToStreamRaw(outStream: BinaryWritable, block: Binary): Int = with(block){
            val index = chunkLoop<Unit>(0, block.limit, TypeSize.long) { outStream.writeLong(retrieveLong(it)) }
            chunkLoop<Unit>(index, block.limit, TypeSize.byte) { outStream.writeByte(retrieveByte(it)) }
            //val index = longLoop<Unit>(limit) { outStream.writeLong(retrieveLong(it)) }
            //byteLoop<Unit>(index, limit) { outStream.writeByte(retrieveByte(it)) }
        }

        public fun unfoldFromStreamRaw(inStream: BinaryReadable, block: Binary): Int = with(block) {
            val index = chunkLoop<Unit>(0, limit, TypeSize.long) { storeLong(it, inStream.readLong()) }
            chunkLoop<Unit>(index, limit, TypeSize.byte) { storeByte(it, inStream.readByte()) }
            //val index = longLoop<Unit>(limit) { storeLong(it, inStream.readLong()) }
            //byteLoop<Unit>(index, limit) { storeByte(it, inStream.readByte()) }
        }

        /**
         * A false checksum doesn't interrupt data flow but is the last to be checked. Just catch the exception,
         * log it and continue execution in a safe manner.
         * */ // Should this one be removed when there is ChunkType?
        public fun unfoldFromStreamByCheck(inStream: BinaryReadable, key: Long): BlockType {
            require(Unfoldable.getType(inStream, Convention.CHECK))
            val length = Unfoldable.getLength(inStream).toInt()
            require(length <= DataSize._1G.size)
            val hash = Unfoldable.getCheck(inStream)
            val block = BlockType(binOf(length))
            var index = chunkLoop<Unit>(0, length, TypeSize.long) { block.storeLong(it, inStream.readLong()) }
            index = chunkLoop<Unit>(index, length, TypeSize.byte) { block.storeByte(it, inStream.readByte()) }
            require(Unfoldable.getEnd(inStream, Convention.CHECK))
            check(block.block.checkSum(key) == hash) { "Block of data is corrupt" }
            return block
        }

        public fun unfoldFromStream(inStream: BinaryReadable): BlockType =
            unfoldFromStreamByConvention(inStream, conventionType)
    }
}