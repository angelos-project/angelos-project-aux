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
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import kotlin.jvm.JvmInline

@JvmInline
public value class CheckType(public val block: Binary) : Storable, Retrievable, Enfoldable {

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

    override fun foldSize(foldFormat: FoldFormat): Int = when (foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> block.limit + Enfoldable.OVERHEAD_LENGTH + Enfoldable.CHECK_SIZE
    }

    public override fun enfoldStream(outStream: BinaryWritable): Int = enfoldToStreamByCheck(outStream, 0)

    /**
     * Allows for packaging arbitrary data blocks which uses hashing that can be verified, also a 64-bit key
     * can be used to XOR the checksum, should give decent protection against forceful corruption attempts of
     * signed data. However, not cryptographically proven yet.
     * */
    /*public fun enfoldToStreamByCheck(outStream: BinaryWritable, key: Long): Int {
        Enfoldable.setType(outStream, conventionType)
        val length = foldSize(FoldFormat.BLOCK).toLong()
        Enfoldable.setLength(outStream, length)
        Enfoldable.setCheck(outStream, block.checkSum(key))
        outStream.measureBytes(length) { BlockType.enfoldToStreamRaw(outStream, block).toLong() }
        Enfoldable.setEnd(outStream, conventionType)
        return length.toInt() + Enfoldable.OVERHEAD_LENGTH + Enfoldable.CHECK_SIZE
    }*/

    public fun enfoldToStreamByCheck(outStream: BinaryWritable, key: Long = 0): Int = outStream.measureBytes {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setLength(outStream, foldSize(FoldFormat.BLOCK)) // FoldFormat.BLOCK ?
        Enfoldable.setCheck(outStream, block.checkSum(key))
        BlockType.enfoldToStreamRaw(outStream, block)
        Enfoldable.setEnd(outStream, conventionType)
    }.toInt()


    public companion object : Unfoldable<CheckType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.CHECK
        override val atomicSize: Int = 0

        public override fun unfoldStream(inStream: BinaryReadable): CheckType = unfoldFromStreamByCheck(inStream, 0)

        /**
         * A false checksum doesn't interrupt data flow but is the last to be checked. Just catch the exception,
         * log it and continue execution in a safe manner.
         * */
        public fun unfoldFromStreamByCheck(inStream: BinaryReadable, key: Long): CheckType {
            require(Unfoldable.getType(inStream, conventionType))
            val length = Unfoldable.getLength(inStream)
            require(length <= DataSize._1G.size)
            val hash = Unfoldable.getCheck(inStream)
            val block = inStream.measureBytes(length) {
                CheckType(binOf(length)).apply { BlockType.unfoldFromStreamRaw(inStream, this.block) }
            }
            require(Unfoldable.getEnd(inStream, conventionType))
            check(block.block.checkSum(key) == hash) { "Block of data is corrupt" }
            return block
        }
    }
}