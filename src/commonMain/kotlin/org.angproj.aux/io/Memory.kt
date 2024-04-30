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
    size: Int, idxOff: Int, idxEnd: Int
): Segment {

    public constructor(size: Int)

    protected val data: Chunk

    override fun create(size: Int, idxOff: Int, idxEnd: Int): Memory

    override fun copyOf(): Memory

    override fun copyOfRange(idxFrom: Int, idxTo: Int): Memory

    override fun getByte(index: Int): Byte

    override fun getShort(index: Int): Short

    override fun getInt(index: Int): Int

    override fun getLong(index: Int): Long

    public companion object {
        public val typeSize: TypeSize
    }
}