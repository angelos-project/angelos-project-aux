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

public class PushPipe<T: PipeType>(
    sink: AbstractSink<T>,
    bufferSize: DataSize = DataSize._4K
): AbstractPipe<T>(
    applySource(sink),
    sink,
    bufferSize
), PushMode {

    internal companion object {
        fun<T: PipeType> applySource(src: AbstractSink<T>): AbstractSource<out PipeType> {
            return when(src) {
                is TextType -> TextSource()
                is BinaryType -> BinarySource()
                is PackageType -> PackageSource()
                else -> { error("Doesn't work") }
            }
        }
    }
}