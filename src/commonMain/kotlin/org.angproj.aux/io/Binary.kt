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
package org.angproj.aux.io

import org.angproj.aux.util.*

public class Binary internal constructor(
    private val _segment: Segment
) : Retrievable, Storable {

    public constructor(size: Int) : this(Bytes(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    public val segment: Segment
        get() = _segment

    public val limit: Int
        get() = _segment.limit

    public val capacity: Int
        get() = _segment.size

    private inline fun <reified E: Reifiable> remaining(position: Int): Int = _segment.limit - position

    private inline fun <reified E: Any> withinReadLimit(position: Int, length: Int, action: () -> E): E {
        require(remaining<Reify>(position) >= length) { "Buffer overflow, limit reached." }
        return action()
    }

    private inline fun <reified E: Any> withinWriteLimit(position: Int, length: Int, action: () -> E) {
        require(remaining<Reify>(position) >= length) { "Buffer overflow, limit reached." }
        action()
    }

    override fun retrieveByte(position: Int): Byte = withinReadLimit(position, TypeSize.byte) {
        _segment.getByte(position) }

    override fun retrieveUByte(position: Int): UByte = withinReadLimit(position, TypeSize.uByte) {
        _segment.getByte(position).toUByte() }

    override fun retrieveShort(position: Int): Short = withinReadLimit(position, TypeSize.short) {
        _segment.getShort(position) }

    override fun retrieveUShort(position: Int): UShort = withinReadLimit(position, TypeSize.uShort) {
        _segment.getShort(position).toUShort() }

    override fun retrieveInt(position: Int): Int = withinReadLimit(position, TypeSize.int) {
        _segment.getInt(position) }

    override fun retrieveUInt(position: Int): UInt = withinReadLimit(position, TypeSize.uInt) {
        _segment.getInt(position).toUInt() }

    override fun retrieveLong(position: Int): Long = withinReadLimit(position, TypeSize.long) {
        _segment.getLong(position) }

    override fun retrieveULong(position: Int): ULong = withinReadLimit(position, TypeSize.uLong) {
        _segment.getLong(position).toULong() }

    override fun retrieveFloat(position: Int): Float = withinReadLimit(position, TypeSize.float) {
        _segment.getInt(position).toFloat() }

    override fun retrieveDouble(position: Int): Double = withinReadLimit(position, TypeSize.double) {
        Double.fromBits(_segment.getLong(position)) }

    override fun storeByte(position: Int, value: Byte): Unit = withinWriteLimit(position, TypeSize.byte) {
        _segment.setByte(position, value) }

    override fun storeUByte(position: Int, value: UByte): Unit = withinWriteLimit(position, TypeSize.uByte) {
        _segment.setByte(position, value.toByte()) }

    override fun storeShort(position: Int, value: Short): Unit = withinWriteLimit(position, TypeSize.short) {
        _segment.setShort(position, value) }

    override fun storeUShort(position: Int, value: UShort): Unit = withinWriteLimit(position, TypeSize.uShort) {
        _segment.setShort(position, value.toShort()) }

    override fun storeInt(position: Int, value: Int): Unit = withinWriteLimit(position, TypeSize.int) {
        _segment.setInt(position, value) }

    override fun storeUInt(position: Int, value: UInt): Unit = withinWriteLimit(position, TypeSize.uInt) {
        _segment.setInt(position, value.toInt()) }

    override fun storeLong(position: Int, value: Long): Unit = withinWriteLimit(position, TypeSize.long) {
        _segment.setLong(position, value) }

    override fun storeULong(position: Int, value: ULong): Unit = withinWriteLimit(position, TypeSize.uLong) {
        _segment.setLong(position, value.toLong()) }

    override fun storeFloat(position: Int, value: Float): Unit = withinWriteLimit(position, TypeSize.float) {
        _segment.setInt(position, value.toBits()) }

    override fun storeDouble(position: Int, value: Double): Unit = withinWriteLimit(position, TypeSize.double) {
        _segment.setLong(position, value.toBits()) }
}

public fun ByteArray.toBinary(): Binary {
    val binary = Binary(size)
    val index = chunkLoop<Reify>(0, binary.limit, TypeSize.long) {
        binary.storeLong(it, readLongAt(it))
    }
    chunkSimple<Reify>(index, binary.limit, Byte.SIZE_BYTES) {
        binary.storeByte(it, this[it])
    }
    return binary
}

public fun Binary.toByteArray(): ByteArray {
    val byteArray = ByteArray(limit)
    val index = chunkLoop<Reify>(0, limit, TypeSize.long) {
        byteArray.writeLongAt(it, retrieveLong(it))
    }
    chunkSimple<Reify>(index, limit, Byte.SIZE_BYTES) {
        byteArray[it] = retrieveByte(it)
    }
    return byteArray
}