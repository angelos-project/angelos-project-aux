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

public abstract class AbstractConcat(
    public override val dataSize: DataSize,
    protected val queueSize: Int
): Reader, Writer, Sizeable {

    override val sizeMode: SizeMode = SizeMode.MAXIMUM

    protected val queue: MutableList<ByteArray> = mutableListOf()

    override fun read(data: ByteArray): Int {
        TODO("Not yet implemented")
    }

    override fun read(length: Int): ByteArray {
        TODO("Not yet implemented")
    }

    override fun write(data: ByteArray): Int {
        TODO("Not yet implemented")
    }
}