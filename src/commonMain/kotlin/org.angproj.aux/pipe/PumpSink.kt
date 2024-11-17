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

import org.angproj.aux.io.PumpWriter
import org.angproj.aux.io.Segment

public class PumpSink(
    private val pump: PumpWriter
): Sink<Nothing>  {
    private var _open: Boolean = true
    private var _staleCnt: Int = 0

    public val staleCnt: Int
        get() = _staleCnt

    public override val count: Long
        get() = pump.inputCount

    /**
     *
     * */
    public fun<reified : Any> absorb(seg: Segment<*>): Int = when {
        !isOpen() -> throw PipeException("Sink is closed")
        !pump.inputStale -> {
            _staleCnt = 0
            pump.write(seg)//.also { if(it < seg.limit) _staleCnt++ }
        }
        _staleCnt >= 2 -> 0.also { close() }
        else -> 0.also { _staleCnt++ }
    }
    /*{
        val size = seg.limit
        return pump.write(seg).also { if (it < size || it == 0) close() }
    }*/

    override fun isOpen(): Boolean = _open

    override fun close() {
        _open = false
    }
}