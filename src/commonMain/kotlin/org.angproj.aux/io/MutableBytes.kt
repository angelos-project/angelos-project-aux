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

public class MutableBytes(size: Int) : Bytes(size), MutableSegment {

    public override fun setByte(index: Int, value: Byte) {
        if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        data[index + idxOff] = value
    }

    public override fun setShort(index: Int, value: Short) {
        if(index !in 0..<(size-1)) throw IllegalArgumentException("Out of bounds.")
        data.setShort(index + idxOff, value)
    }

    public override fun setInt(index: Int, value: Int) {
        if(index !in 0..<(size-3)) throw IllegalArgumentException("Out of bounds.")
        data.setInt(index + idxOff, value)
    }

    public override fun setLong(index: Int, value: Long) {
        if(index !in 0..<(size-7)) throw IllegalArgumentException("Out of bounds.")
        data.setLong(index + idxOff, value)
    }

    private fun ByteArray.setShort(index: Int, value: Short) {
        this[index + 1] = (value.toInt() ushr 8).toByte()
        this[index] = value.toByte()
    }

    private fun ByteArray.setInt(index: Int, value: Int) {
        this.setShort(index + 2, (value ushr 16).toShort())
        this.setShort(index, value.toShort())
    }

    private fun ByteArray.setLong(index: Int, value: Long)  {
        this.setInt(index + 4, (value ushr 32).toInt())
        this.setInt(index, value.toInt())
    }

    private fun ByteArray.revSetShort(index: Int, value: Short) {
        this[index] = (value.toInt() ushr 8).toByte()
        this[index + 1] = value.toByte()
    }

    private fun ByteArray.revSetInt(index: Int, value: Int) {
        this.revSetShort(index, (value ushr 16).toShort())
        this.revSetShort(index + 2, value.toShort())
    }

    private fun ByteArray.revSetLong(index: Int, value: Long)  {
        this.revSetInt(index, (value ushr 32).toInt())
        this.revSetInt(index + 4, value.toInt())
    }
}