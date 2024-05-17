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

public class PullPipe<T: PipeType>(
    src: AbstractSource<T>,
    bufferSize: DataSize = DataSize._4K
): AbstractPipe<T>(
    src,
    applySink(src),
    bufferSize
), PullMode {

    public fun getTextReadable(): TextSink = when(sink) {
        is TextSink -> sink
        else -> throw UnsupportedOperationException("Doesn't support text!")
    }

    public fun getPackageReadable(): PackageSink = when(sink) {
        is PackageSink -> sink
        else -> throw UnsupportedOperationException("Doesn't support package!")
    }

    public fun getBinaryReadable(): BinarySink = when(sink) {
        is BinarySink -> sink
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