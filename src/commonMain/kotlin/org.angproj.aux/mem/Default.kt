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
package org.angproj.aux.mem

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect object Default: MemoryManager {
    override fun allocate(dataSize: DataSize): Segment

    override fun recycle(segment: Segment)
}

public fun interface Set64 {
    public fun invoke(pos: Int, value: Long)
}

public fun interface Set8 {
    public fun invoke(pos: Int, value: Byte)
}

public fun interface Get64 {
    public fun invoke(pos: Int): Long
}

public fun interface Get8 {
    public fun invoke(pos: Int): Byte
}

public interface Copy {
    public val set64: Set64
    public val set8: Set8
    public val get64: Get64
    public val get8: Get8

    private inline fun bla() {

    }
}