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

import org.angproj.aux.buf.*
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class Segment(
    size: Int, idxSize: TypeSize, idxOff: Int, idxEnd: Int
): AbstractSpeedCopy(size, idxSize, idxOff, idxEnd), ByteString {

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(idxEnd, idxSize)
    final override val marginSized: Int = SpeedCopy.addMarginByIndexType(idxEnd, idxSize)

    public inline fun <reified T: Reifiable> Long.reverse(): Long = (
            this.toInt().reverse<Reify>().toLong() shl 32) or (
            this ushr 32).toInt().reverse<Reify>().toLong()

    public inline fun <reified T: Reifiable> Int.reverse(): Int = (
            this.toShort().reverse<Reify>().toInt() shl 16) or (
            this ushr 16).toShort().reverse<Reify>().toInt()

    public inline fun <reified T: Reifiable> Short.reverse(): Short = (
            (this.toInt() shl 16) or (this.toInt() ushr 16)).toShort()
}

@PublishedApi
internal inline fun <reified S: Segment> S.copyOfRange(idxFrom: Int, idxTo: Int): S = innerCopyOfRange(idxFrom, idxTo)