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

public interface Pipe<M: PipeMode, T: PipeType> {

    public fun isPull(): Boolean = this is PipeMode.PULL
    public fun isPush(): Boolean = this is PipeMode.PUSH

    public fun isText(): Boolean = this is PipeType.TXT
    public fun isBinary(): Boolean = this is PipeType.BIN
    public fun isPackage(): Boolean = this is PipeType.PKG

    public companion object{
        internal fun<T: PipeType> combineWithSink(src: Source<in T>): AbstractSink<out PipeType> = when(src) {
            is PipeType.TXT -> GlyphSink()
            is PipeType.BIN -> BinarySink()
            is PipeType.PKG -> PackageSink()
            else -> error("Unsupported source type.")
        }

        internal fun<T: PipeType> combineWithSource(sink: Sink<in T>): AbstractSource<out PipeType> = when(sink) {
            is PipeType.TXT -> GlyphSource()
            is PipeType.BIN -> BinarySource()
            is PipeType.PKG -> PackageSource()
            else -> error("Unsupported source type.")
        }
    }
}