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
package org.angproj.aux.pipe

import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment

public class PumpSource(
    private val pump: PumpReader
): Source<Nothing>  {
    private var _open: Boolean = true
    private var _staleCnt: Int = 0

    public val staleCnt: Int
        get() = _staleCnt

    public override val count: Long
        get() = pump.outputCount

    /**
     * This function forcefully sets the limit of the segment to the returned value
     * to avoid programming mistakes.
     * */
    public fun<reified : Any> squeeze(seg: Segment<*>): Int = when {
        !isOpen() -> throw PipeException("Source is closed")
        !pump.outputStale -> {
            _staleCnt = 0
            pump.read(seg).also { if(pump.outputStale || it == 0) _staleCnt++ }
        }
        _staleCnt >= 3 -> 0.also { close() }
        else -> 0.also { _staleCnt++ }
    }

    override fun isOpen(): Boolean = _open

    override fun close() { _open = false }
}