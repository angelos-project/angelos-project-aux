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
package org.angproj.aux.util

import kotlin.jvm.JvmInline

@JvmInline
public value class BitArray(private val array: ByteArray) : Collection<Boolean> {
    override val size: Int
        get() = array.size * 8

    override fun isEmpty(): Boolean = array.isEmpty()

    override fun containsAll(elements: Collection<Boolean>): Boolean { throw UnsupportedOperationException() }

    override fun contains(element: Boolean): Boolean { throw UnsupportedOperationException() }

    override fun iterator(): Iterator<Boolean> = BitIterator(array)
}

public class BitIterator(private val bytes: ByteArray): Iterator<Boolean> {
    private val size = bytes.size * 8
    private var position = 0

    override fun hasNext(): Boolean = position < size

    override fun next(): Boolean {
        val index = position / 8
        return when((position++).floorMod(8)) {
            0 -> bytes[index].checkFlag7()
            1 -> bytes[index].checkFlag6()
            2 -> bytes[index].checkFlag5()
            3 -> bytes[index].checkFlag4()
            4 -> bytes[index].checkFlag3()
            5 -> bytes[index].checkFlag2()
            6 -> bytes[index].checkFlag1()
            7 -> bytes[index].checkFlag0()
            else -> error("Won't happen!")
        }
    }
}