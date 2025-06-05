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

public class BitIterator(private val bytes: BinaryReadable) : Iterator<Boolean> {
    private var current = bytes.readByte().toInt()
    private var position = 8

    override fun hasNext(): Boolean = if(position > 0) true else try {
        current = bytes.readByte().toInt()
        position = 8
        true
    } catch (_: InputOutputException) { false }

    override fun next(): Boolean = (((current ushr --position) and 0x1) == 1)
}


public fun <S: BinaryReadable> S.bitIter(): BitIterator = BitIterator(this)