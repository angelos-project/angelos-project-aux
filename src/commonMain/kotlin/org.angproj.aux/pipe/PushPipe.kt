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

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment

public class PushPipe<T: PipeType>(
    sink: AbstractSink<T>,
    bufferSize: DataSize = DataSize._4K
): AbstractPipe<T>(
    applySource(sink),
    sink,
    bufferSize
), PushMode {

    override val buf: MutableList<Segment> = mutableListOf()

    /**
     * Will drain all data.
     * */
    override fun drain() {
        do {
            val seg = buf.pop()
            sink.absorb(seg)
            seg.close()
        } while(buf.isNotEmpty())
    }

    public fun getTextWritable(): TextSource = when(src) {
        is TextSource -> src //.also { if(buf.isEmpty()) tap() }.also { it.loadSegment() }
        else -> throw UnsupportedOperationException("Doesn't support text!")
    }

    public fun getPackageWritable(): PackageSource = when(src) {
        is PackageSource -> src//.also { if(buf.isEmpty()) tap() }.also { it.loadSegment() }
        else -> throw UnsupportedOperationException("Doesn't support package!")
    }

    public fun getBinaryWritable(): BinarySource = when(src) {
        is BinarySource -> src//.also { if(buf.isEmpty()) tap() }.also { it.loadSegment() }
        else -> throw UnsupportedOperationException("Doesn't support binary!")
    }

    internal companion object {
        fun<T: PipeType> applySource(sink: AbstractSink<T>): AbstractSource<out PipeType> {
            return when(sink) {
                is TextType -> TextSource()
                is BinaryType -> BinarySource()
                is PackageType -> PackageSource()
                else -> { error("Doesn't work") }
            }
        }
    }
}