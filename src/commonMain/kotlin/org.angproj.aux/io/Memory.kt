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

import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect open class Memory protected constructor(
    size: Int, idxLimit: Int
): AbstractMemory {

    public constructor(size: Int)

    final override val data: Chunk

    override fun create(size: Int, idxLimit: Int): Memory

    override fun getByte(index: Int): Byte

    override fun getShort(index: Int): Short

    override fun getInt(index: Int): Int

    override fun getLong(index: Int): Long

    override fun setByte(index: Int, value: Byte)

    override fun setShort(index: Int, value: Short)

    override fun setInt(index: Int, value: Int)

    override fun setLong(index: Int, value: Long)
}