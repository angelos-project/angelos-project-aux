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
package org.angproj.aux.pipe.pull

import org.angproj.aux.buf.Pump
import org.angproj.aux.io.*
import org.angproj.aux.util.Reifiable

public abstract class AbstractSource<T: PipeType>(
    protected val pump: PumpReader = Pump
): Source, PipeType {
    private var _open: Boolean = true

    public fun<reified : Reifiable> squeeze(seg: Segment): Int {
        val size = seg.limit
        return pump.read(seg).also { if (it < size || it == 0) close() }
    }

    override fun isOpen(): Boolean = _open

    override fun close() {
        _open = false
    }
}