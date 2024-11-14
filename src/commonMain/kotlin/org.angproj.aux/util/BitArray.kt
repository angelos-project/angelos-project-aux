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

import org.angproj.aux.buf.asWrapped
import org.angproj.aux.io.*
import kotlin.jvm.JvmInline

@JvmInline
public value class BitArray(private val array: ByteArray) : Collection<Boolean> {
    override val size: Int
        get() = array.size * 8

    override fun isEmpty(): Boolean = array.isEmpty()

    override fun containsAll(elements: Collection<Boolean>): Boolean {
        throw UnsupportedOperationException()
    }

    override fun contains(element: Boolean): Boolean {
        throw UnsupportedOperationException()
    }

    override fun iterator(): Iterator<Boolean> = BitIterator(array.toBinary().asWrapped())
}

