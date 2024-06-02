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
import kotlin.math.min

public class PullPipe<T: PipeType>(
    src: AbstractSource<T>,
    bufferSize: DataSize = DataSize._4K
): AbstractPipe<T>(
    src,
    applySink(src),
    bufferSize
), PullMode {

    override val buf: MutableList<Segment> = mutableListOf()

    /**
     * Will tap up data as long there is leftover and will quit when the segment is not fully filled.
     * */
    override fun tap() {
        var leftover = bufferSize.size - usedSize

        if(leftover > 0) do {
            val seg = Bytes(min(segmentSize.size, leftover))
            src.squeeze(seg)
            leftover -= seg.limit
            buf.push(seg)
        } while(seg.limit == seg.size && leftover > 0)
    }

    public fun getTextReadable(): TextSink = when(sink) {
        is TextSink -> sink.also { if(buf.isEmpty()) tap() }.also { it.loadSegment() }
        else -> throw UnsupportedOperationException("Doesn't support text!")
    }

    public fun getPackageReadable(): PackageSink = when(sink) {
        is PackageSink -> sink.also { if(buf.isEmpty()) tap() }.also { it.loadSegment() }
        else -> throw UnsupportedOperationException("Doesn't support package!")
    }

    public fun getBinaryReadable(): BinarySink = when(sink) {
        is BinarySink -> sink.also { if(buf.isEmpty()) tap() }.also { it.loadSegment() }
        else -> throw UnsupportedOperationException("Doesn't support binary!")
    }

    internal companion object {
        fun<T: PipeType> applySink(src: AbstractSource<T>): AbstractSink<out PipeType> {
            return when(src) {
                is TextType -> TextSink()
                is BinaryType -> BinarySink()
                is PackageType -> PackageSink()
                else -> { error("Doesn't work") }
            }
        }
    }
}