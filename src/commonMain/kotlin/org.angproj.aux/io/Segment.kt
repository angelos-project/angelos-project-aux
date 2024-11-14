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

import org.angproj.aux.mem.Default
import org.angproj.aux.mem.MemoryManager
import org.angproj.aux.rand.InitializationVector
import org.angproj.aux.util.AbstractBufferAware
import org.angproj.aux.util.Copy
import org.angproj.aux.util.NullObject
import kotlin.jvm.JvmStatic

public abstract class Segment<S: Segment<S>>(
    size: Int, mem: MemoryManager<S>
): AbstractBufferAware(), ByteString, Comparable<Segment<*>> {

    protected val memCtx: MemoryManager<S> = mem

    private var _closed = false

    public open val isOpen: Boolean
        get() = !_closed

    protected fun close(action: () -> Unit) {
        if(isOpen) {
            _closed = true
            action()
        }
    }

    public override val size: Int = size

    protected var _limit: Int = size
    override val limit: Int
        get() = _limit

    override fun limitAt(newLimit: Int) {
        require(newLimit in 0..size) { "Proposed limit is outside boundaries." }
        _limit = newLimit
    }

    protected inline fun <reified R: Any> Int.checkRangeByte(): Unit = when(this) {
        !in 0..<_limit -> throw IllegalArgumentException("Out of bounds. Byte - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeShort(): Unit = when(this) {
        !in 0..<_limit-1 -> throw IllegalArgumentException("Out of bounds. Short - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeInt(): Unit = when(this) {
        !in 0..<_limit-3 -> throw IllegalArgumentException("Out of bounds. Int - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeLong(): Unit = when(this) {
        !in 0..<_limit-7 -> throw IllegalArgumentException("Out of bounds. Long - $this")
        else -> Unit
    }

    /*protected inline fun<reified R: Any> joinLong(u: Int, l: Int): Long = ((u.toLong() shl 32 and -0x100000000) or (l.toLong() and 0xFFFFFFFF))
    protected inline fun<reified R: Any> joinInt(u: Short, l: Short): Int = (u.toInt() shl 16 and -0x10000) or (l.toInt() and 0xFFFF)
    protected inline fun<reified R: Any> joinShort(u: Byte, l: Byte): Short = ((u.toInt() shl 8 and 0xFF00) or (l.toInt() and 0xFF)).toShort()

    protected inline fun<reified R: Any> upperLong(value: Long): Int = (value ushr 32).toInt()
    protected inline fun<reified R: Any> upperInt(value: Int): Short = (value ushr 16).toShort()
    protected inline fun<reified R: Any> upperShort(value: Short): Byte = (value.toInt() ushr 8).toByte()

    protected inline fun<reified R: Any> lowerLong(value: Long): Int = value.toInt()
    protected inline fun<reified R: Any> lowerInt(value: Int): Short = value.toShort()
    protected inline fun<reified R: Any> lowerShort(value: Short): Byte = value.toByte()

    protected inline fun<reified R: Any> swapLong(value: Long): Long = joinLong<R>(swapInt<R>(lowerLong<R>(value)), swapInt<R>(upperLong<R>(value)))
    protected inline fun<reified R: Any> swapInt(value: Int): Int = joinInt<R>(swapShort<R>(lowerInt<R>(value)), swapShort<R>(upperInt<R>(value)))
    protected inline fun<reified R: Any> swapShort(value: Short): Short = joinShort<R>(lowerShort<R>(value), upperShort<R>(value))*/

    @Deprecated("To disappear")
    public inline fun <reified T: Any> Long.reverse(): Long = (
            this.toInt().reverse<Unit>().toLong() shl 32) or (
            this ushr 32).toInt().reverse<Unit>().toLong()

    @Deprecated("To disappear")
    public inline fun <reified T: Any> Int.reverse(): Int = (
            this.toShort().reverse<Unit>().toInt() shl 16) or (
            this ushr 16).toShort().reverse<Unit>().toInt()

    @Deprecated("To disappear")
    public inline fun <reified T: Any> Short.reverse(): Short = (
            (this.toInt() shl 16) or (this.toInt() ushr 16)).toShort()

    public override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null || this::class != other::class) return false
        other as Segment<*>
        return compareTo(other) == 0
    }

    /**
     * Advanced segment hash algorithm.
     * */
    public override fun hashCode(): Int {
        var result: Long = InitializationVector.IV_CA96.iv
        when(_limit < 8) {
            true -> (0 until _limit).forEach { result = result * 31 + getByte(it) }
            else -> {
                (0 until _limit-7 step 8).forEach { result = (-result.inv() * 7) xor getLong(it) }
                result = (-result.inv() * 7) xor getLong(_limit-8)
            }
        }
        return (result ushr 32).toInt() xor result.toInt()
    }

    public override operator fun compareTo(other: Segment<*>): Int { return hashCode() - other.hashCode() }

    public companion object {
        @Deprecated("No use")
        @JvmStatic
        public fun addMarginInTotalBytes(indexCount: Int, typeSize: TypeSize): Int {
            require(indexCount >= 0)

            val byteCount = indexCount * typeSize.size
            val remainder = byteCount % TypeSize.long
            return (byteCount + if(remainder == 0) 0 else TypeSize.long - remainder)
        }

        /**
         * The xCnt variable sets the amount of x indices in an array-like buffer if x is the variable type used.
         * The xLen2addMargin adds enough of invisible indices to equal a divisible size of type Long, described in
         * type x.
         * */
        @Deprecated("No use")
        @JvmStatic
        public fun addMarginByIndexType(indexCount: Int, typeSize: TypeSize): Int {
            require(indexCount >= 0)

            val factor = TypeSize.LONG.size / typeSize.size
            val remainder = indexCount % factor
            return (indexCount + if(remainder == 0) 0 else factor - remainder)
        }
    }
}

public fun<S: Segment<S>, D: Segment<D>> S.copyInto(
    destination: D, destinationOffset: Int, fromIndex: Int, toIndex: Int
): Int = object : Copy {
    operator fun invoke(): Int {
        check(isOpen && destination.isOpen) { "Closed memory" }
        require(fromIndex, toIndex, destinationOffset, this@copyInto, destination)
        return innerCopy(fromIndex, toIndex, destinationOffset, this@copyInto, destination)
    }
}()

public fun<S: Segment<S>> S.copyOfRange(
    fromIndex: Int, toIndex: Int
): Bytes = Default.allocate(toIndex - fromIndex).apply {
        this@copyOfRange.copyInto(this@apply, 0, fromIndex, toIndex) }

public fun<S: Segment<S>> S.copyOf(): Bytes = copyOfRange(0, limit)

public fun Segment<*>.isNull(): Boolean = NullObject.segment === this

private val nullSegment = object : Segment<Nothing>(0, object : MemoryManager<Nothing>{
    private fun none(): Nothing { throw UnsupportedOperationException( "Segment NULL is reached, EOF!" ) }
    override fun allocate(dataSize: DataSize): Nothing { none() }
    override fun recycle(segment: Nothing) { none() }
}) {
    private fun none(): Nothing { throw UnsupportedOperationException( "Segment NULL is reached, EOF!" ) }
    override fun getByte(index: Int): Byte { none() }
    override fun getShort(index: Int): Short { none() }
    override fun getInt(index: Int): Int { none() }
    override fun getLong(index: Int): Long { none() }
    override fun getRevShort(index: Int): Short { none() }
    override fun getRevInt(index: Int): Int { none() }
    override fun getRevLong(index: Int): Long { none() }
    override fun setByte(index: Int, value: Byte) { none() }
    override fun setShort(index: Int, value: Short) { none() }
    override fun setInt(index: Int, value: Int) { none() }
    override fun setLong(index: Int, value: Long) { none() }
    override fun setRevShort(index: Int, value: Short) { none() }
    override fun setRevInt(index: Int, value: Int) { none() }
    override fun setRevLong(index: Int, value: Long) { none() }

    override fun close() { none() }
}
public val NullObject.segment: Segment<*>
    get() = nullSegment