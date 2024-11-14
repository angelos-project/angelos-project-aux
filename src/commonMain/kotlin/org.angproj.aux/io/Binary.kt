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

import org.angproj.aux.buf.copyInto
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.rand.InitializationVector
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.*


public class Binary(
    segment: Segment<*>, view: Boolean = false
) : MemBlock(segment, view), Retrievable, Storable/*, NumberAware, BufferAware*/ {

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
        _segment.getByte(position).conv2uB<Unit>()

    override fun retrieveShort(position: Int): Short =
        _segment.getShort(position)

    override fun retrieveUShort(position: Int): UShort =
        _segment.getShort(position).conv2uS<Unit>()

    override fun retrieveInt(position: Int): Int =
        _segment.getInt(position)

    override fun retrieveUInt(position: Int): UInt =
        _segment.getInt(position).conv2uI<Unit>()

    override fun retrieveLong(position: Int): Long =
        _segment.getLong(position)

    override fun retrieveULong(position: Int): ULong =
        _segment.getLong(position).conv2uL<Unit>()

    override fun retrieveFloat(position: Int): Float =
       _segment.getInt(position).conv2F<Unit>()

    override fun retrieveDouble(position: Int): Double =
        _segment.getLong(position).conv2D<Unit>()

    override fun retrieveRevShort(position: Int): Short =
        _segment.getRevShort(position)

    override fun retrieveRevUShort(position: Int): UShort =
        _segment.getRevShort(position).conv2uS<Unit>()

    override fun retrieveRevInt(position: Int): Int =
        _segment.getRevInt(position)

    override fun retrieveRevUInt(position: Int): UInt =
        _segment.getRevInt(position).conv2uI<Unit>()

    override fun retrieveRevLong(position: Int): Long =
        _segment.getRevLong(position)

    override fun retrieveRevULong(position: Int): ULong =
        _segment.getRevLong(position).conv2uL<Unit>()

    override fun retrieveRevFloat(position: Int): Float =
        _segment.getRevInt(position).conv2F<Unit>()

    override fun retrieveRevDouble(position: Int): Double =
        _segment.getRevLong(position).conv2D<Unit>()

    override fun storeByte(position: Int, value: Byte): Unit =
        _segment.setByte(position, value)

    override fun storeUByte(position: Int, value: UByte): Unit =
        _segment.setByte(position, value.conv2B<Unit>())

    override fun storeShort(position: Int, value: Short): Unit =
        _segment.setShort(position, value)

    override fun storeUShort(position: Int, value: UShort): Unit =
        _segment.setShort(position, value.conv2S<Unit>())

    override fun storeInt(position: Int, value: Int): Unit =
        _segment.setInt(position, value)

    override fun storeUInt(position: Int, value: UInt): Unit =
        _segment.setInt(position, value.conv2I<Unit>())

    override fun storeLong(position: Int, value: Long): Unit =
        _segment.setLong(position, value)

    override fun storeULong(position: Int, value: ULong): Unit =
        _segment.setLong(position, value.conv2L<Unit>())

    override fun storeFloat(position: Int, value: Float): Unit =
        _segment.setInt(position, value.conv2I<Unit>())

    override fun storeDouble(position: Int, value: Double): Unit =
        _segment.setLong(position, value.conv2L<Unit>())

    override fun storeRevShort(position: Int, value: Short): Unit =
        _segment.setRevShort(position, value)

    override fun storeRevUShort(position: Int, value: UShort): Unit =
        _segment.setRevShort(position, value.conv2S<Unit>())

    override fun storeRevInt(position: Int, value: Int): Unit =
        _segment.setRevInt(position, value)

    override fun storeRevUInt(position: Int, value: UInt): Unit =
        _segment.setRevInt(position, value.conv2I<Unit>())

    override fun storeRevLong(position: Int, value: Long): Unit =
        _segment.setRevLong(position, value)

    override fun storeRevULong(position: Int, value: ULong): Unit =
        _segment.setRevLong(position, value.conv2L<Unit>())

    override fun storeRevFloat(position: Int, value: Float): Unit =
        _segment.setRevInt(position, value.conv2I<Unit>())

    override fun storeRevDouble(position: Int, value: Double): Unit =
        _segment.setRevLong(position, value.conv2L<Unit>())

    public fun checkSum(key: Long = 0): Long {
        var result: Long = InitializationVector.IV_CA96.iv xor key
        with(_segment) {
            when(limit < 8) {
                true -> (0 until limit).forEach { result = result * 31 + getByte(it) }
                else -> {
                    (0 until limit-7 step 8).forEach { result = (-result.inv() * 13) xor getLong(it) }
                    result = (-result.inv() * 13) xor getLong(limit-8)
                }
            }
        }
        return result
    }
}

public fun binOf(capacity: Int): Binary = BufMgr.bin(capacity)

public fun Binary.securelyRandomize() { SecureRandom.read(_segment) }

public fun ByteArray.toBinary(): Binary = withBufferAware(this){
    val binary = binOf(it.size)
    val index = chunkLoop<Unit>(0, binary.limit, TypeSize.long) { v ->
        binary.storeLong(v, it.readLongAt(v))
    }
    chunkLoop<Unit>(index, binary.limit, TypeSize.byte) { v ->
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

public operator fun Binary.plus(other: Binary): MutableList<Binary> = mutableListOf(this, other)

public operator fun MutableList<Binary>.plus(other: Binary): MutableList<Binary> = also { add(other) }

public operator fun MutableList<Binary>.plusAssign(other: Binary) { add(other) }

public fun List<Binary>.toBinary(): Binary {
    val out = binOf(sumOf { it.limit })
    var dstOff = 0
    forEach {
        it.copyInto(out, dstOff, 0, it.limit)
        dstOff += it.limit
    }
    return out
}

/**
 * It is hereby prohibited to use NullObject.binary for explicit use.
 * */
public fun Binary.isNull(): Boolean = NullObject.binary === this
private val nullBinary = Binary(NullObject.segment)
public val NullObject.binary: Binary
    get() = throw UnsupportedOperationException("Hereby prohibited to use!")