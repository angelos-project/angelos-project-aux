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
package org.angproj.aux.buf

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.TextReadable

public class PullPipe<T: PipeType>(
    src: AbstractSource<T>,
    bufferSize: DataSize = DataSize._4K
): AbstractPipe<PipeMode.PULL, T>(
    src,
    Pipe.combineWithSink(src),
    bufferSize
) {
    public fun getReadable(): TextReadable = when(isText()) {
        true -> sink as GlyphSink
        else -> error("Not readable sink")
    }
}