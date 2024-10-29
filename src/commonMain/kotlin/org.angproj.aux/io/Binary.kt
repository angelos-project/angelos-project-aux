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

import org.angproj.aux.mem.BufMgr
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.*


public class Binary(
    segment: Segment<*>, view: Boolean = false
) : MemBlock(segment, view), Retrievable, Storable, NumberAware/*, BufferAware*/ {

    //public constructor(size: Int) : this(Bytes(size))

    //public constructor(size: DataSize = DataSize._4K) : this(size.size)

    /*private inline fun <reified E: Any> withinReadLimit(position: Int, length: Int, action: () -> E): E {
        require(remaining<Int>(position) >= length) { "Buffer overflow, limit reached." }
        return action()
    }

    private inline fun <reified E: Any> withinWriteLimit(position: Int, length: Int, action: () -> E) {
        require(remaining<Int>(position) >= length) { "Buffer overflow, limit reached." }
        action()
    }*/

    override fun retrieveByte(position: Int): Byte =
        _segment.getByte(position)

    override fun retrieveUByte(position: Int): UByte =
        _segment.getByte(position).conv2uB()

    override fun retrieveShort(position: Int): Short =
        _segment.getShort(position)

    override fun retrieveUShort(position: Int): UShort =
        _segment.getShort(position).conv2uS()

    override fun retrieveInt(position: Int): Int =
        _segment.getInt(position)

    override fun retrieveUInt(position: Int): UInt =
        _segment.getInt(position).conv2uI()

    override fun retrieveLong(position: Int): Long =
        _segment.getLong(position)

    override fun retrieveULong(position: Int): ULong =
        _segment.getLong(position).conv2uL()

    override fun retrieveFloat(position: Int): Float =
       _segment.getInt(position).conv2F()

    override fun retrieveDouble(position: Int): Double =
        _segment.getLong(position).conv2D()

    override fun storeByte(position: Int, value: Byte): Unit =
        _segment.setByte(position, value)

    override fun storeUByte(position: Int, value: UByte): Unit =
        _segment.setByte(position, value.conv2B())

    override fun storeShort(position: Int, value: Short): Unit =
        _segment.setShort(position, value)

    override fun storeUShort(position: Int, value: UShort): Unit =
        _segment.setShort(position, value.conv2S())

    override fun storeInt(position: Int, value: Int): Unit =
        _segment.setInt(position, value)

    override fun storeUInt(position: Int, value: UInt): Unit =
        _segment.setInt(position, value.conv2I())

    override fun storeLong(position: Int, value: Long): Unit =
        _segment.setLong(position, value)

    override fun storeULong(position: Int, value: ULong): Unit =
        _segment.setLong(position, value.conv2L())

    override fun storeFloat(position: Int, value: Float): Unit =
        _segment.setInt(position, value.conv2I())

    override fun storeDouble(position: Int, value: Double): Unit =
        _segment.setLong(position, value.conv2L())
}

public fun binOf(capacity: Int): Binary = BufMgr.bin(capacity)

public fun Binary.securelyRandomize() { SecureRandom.read(_segment) }

public fun ByteArray.toBinary(): Binary = withBufferAware(this){
    val binary = binOf(it.size)
    val index = chunkLoop<Reify>(0, binary.limit, TypeSize.long) { v ->
        binary.storeLong(v, it.readLongAt(v))
    }
    chunkLoop<Reify>(index, binary.limit, TypeSize.byte) { v ->
        binary.storeByte(v, it[v])
    }
    return binary
}

public fun<C: Segment<*>> Binary.copyInto(
    destination: C, destinationOffset: Int, fromIndex: Int, toIndex: Int
) : Unit = object : Copy {
    operator fun invoke() {
        check(_segment.isOpen && destination.isOpen) { "Closed memory" }
        require(fromIndex, toIndex, destinationOffset, _segment, destination)
        innerCopy(fromIndex, toIndex, destinationOffset, _segment, destination)
    }
}()

/**
 * It is hereby prohibited to use NullObject.binary for explicit use.
 * */
public fun Binary.isNull(): Boolean = NullObject.binary === this
private val nullBinary = Binary(NullObject.segment)
public val NullObject.binary: Binary
    get() = throw UnsupportedOperationException("Hereby prohibited to use!")