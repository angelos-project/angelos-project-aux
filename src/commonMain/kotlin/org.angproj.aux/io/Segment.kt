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

import org.angproj.aux.buf.AbstractSpeedCopy
import org.angproj.aux.buf.SpeedCopy
import org.angproj.aux.buf.copyInto
import org.angproj.aux.buf.copyOf
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class Segment(
    size: Int, idxSize: TypeSize, idxLimit: Int
): AbstractSpeedCopy(size, idxSize, idxLimit), ByteString {

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(idxLimit, idxSize)
    final override val marginSized: Int = SpeedCopy.addMarginByIndexType(idxLimit, idxSize)

    public inline fun <reified T: Reifiable> Long.reverse(): Long = (
            this.toInt().reverse<Reify>().toLong() shl 32) or (
            this ushr 32).toInt().reverse<Reify>().toLong()

    public inline fun <reified T: Reifiable> Int.reverse(): Int = (
            this.toShort().reverse<Reify>().toInt() shl 16) or (
            this ushr 16).toShort().reverse<Reify>().toInt()

    public inline fun <reified T: Reifiable> Short.reverse(): Short = (
            (this.toInt() shl 16) or (this.toInt() ushr 16)).toShort()
}

public fun<T: Segment> T.copyInto(
    destination: T, destinationOffset: Int, fromIndex: Int, toIndex: Int
): Unit = copyInto(destination, destinationOffset, fromIndex, toIndex)

public fun<T: Segment> T.copyOfRange(
    fromIndex: Int, toIndex: Int): T = copyOfRange(fromIndex, toIndex)

public fun<T: Segment> T.copyOf(): T = copyOf()


public fun Segment.isNull(): Boolean = NullObject.segment === this

private val nullSegment = object : Segment(0, TypeSize.INT, 0) {
    private fun none(): Nothing { throw UnsupportedOperationException() }
    override fun create(size: Int, idxLimit: Int): AbstractSpeedCopy { none() }
    override fun getByte(index: Int): Byte { none() }
    override fun getShort(index: Int): Short { none() }
    override fun getInt(index: Int): Int { none() }
    override fun getLong(index: Int): Long { none() }
    override fun setByte(index: Int, value: Byte) { none() }
    override fun setShort(index: Int, value: Short) { none() }
    override fun setInt(index: Int, value: Int) { none() }
    override fun setLong(index: Int, value: Long) { none() }
}
public val NullObject.segment: Segment
    get() = nullSegment