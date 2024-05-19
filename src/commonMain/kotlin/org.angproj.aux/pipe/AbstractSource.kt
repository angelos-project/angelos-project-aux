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

import org.angproj.aux.buf.Pump
import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment

public abstract class AbstractSource<T: PipeType>(
    protected val pump: PumpReader = Pump
): AbstractPipePoint<T>(), Source, PipeType {
    internal fun squeeze(seg: Segment, size: Int = seg.length): Int = pump.readInto(seg, size)
}