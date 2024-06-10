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

import org.angproj.aux.io.*
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public class PushPipe<T: PipeType>(
    private val sink: PumpSink<T>,
    segSize: DataSize = DataSize._1K,
    bufSize: DataSize = DataSize._4K
): AbstractPipe<T>(segSize, bufSize) {

    public fun<reified : Reifiable> isCrammed(): Boolean = totSize<Reify>() >= bufSize.size

    /**
     * Will drain all data.
     * */
    public fun<reified : Reifiable> drain() {
        do {
            val seg = buf.pop<Reify>()
            sink.absorb<Reify>(seg)
            recycle<Reify>(seg)
        } while(buf.isNotEmpty() && sink.isOpen())
    }

    public fun<reified : Reifiable> push(seg: Segment): Unit = buf.push<Reify>(seg)

    public fun<reified : Reifiable> isSinkOpen(): Boolean = sink.isOpen()
}

public fun PushPipe<TextType>.getSource(): TextSource = TextSource(this)

public fun PushPipe<BinaryType>.getSource(): BinarySource = BinarySource(this)