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

import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.PumpWriter

/**
 * The [Pipe] represent two sets of pipes available.
 *  1. The first pipe is a [PullPipe] which serves a [Sink] from which data can be read.
 *  The pipe requires a [PumpSource] from which data can be [squeeze]d, when squeezing,
 *  the source will read from a given [PumpReader] which may be an underlying file, socket
 *  or given memory chunk. When the reader is fed a segment from the source it should copy
 *  all available data in the segment, if the segment is large enough, otherwise more reads
 *  are needed. If a segment is half-full it has reached a temporary data-end called EOF,
 *  this does not indicate that the stream is closed, rather in such an event, the segment
 *  is untouched, in the EOF case the segments limit is set lower or to zero.
 *  2. The second pipe is a [PushPipe] which gives a [Source] to which data can be written.
 *  The pipe requires a [PumpSink] to which data can be [absorb]ed, while absorbing, the
 *  sink will by force push data on a given [PumpWriter]
 *
 * */

/**
 * Default Pipe builder using the default memory manager which works as failsafe.
 * */
public object Pipe: PipeBuilder {

    public fun buildTextSink(reader: PumpReader): GlyphSink = buildSink { pull(reader).utf() }
    public fun buildBinarySink(reader: PumpReader): BinarySink = buildSink { pull(reader).bin() }
    public fun buildPackageSink(reader: PumpReader): PackageSink = buildSink { pull(reader).pkg() }

    public fun buildTextSource(writer: PumpWriter): GlyphSource = buildSource { push(writer).utf() }
    public fun buildBinarySource(writer: PumpWriter): BinarySource = buildSource { push(writer).bin() }
    public fun buildPackageSource(writer: PumpWriter): PackageSource = buildSource { push(writer).pkg() }

    public fun buildTextPullPipe(reader: PumpReader): GlyphSink = buildTextSink(reader)
    public fun buildBinaryPullPipe(reader: PumpReader): BinarySink = buildBinarySink(reader)
    public fun buildPackagePullPipe(reader: PumpReader): PackageSink = buildPackageSink(reader)

    public fun buildTextPushPipe(writer: PumpWriter): GlyphSource = buildTextSource(writer)
    public fun buildBinaryPushPipe(writer: PumpWriter): BinarySource = buildBinarySource(writer)

    public fun buildPackagePushPipe(writer: PumpWriter): PackageSource = buildPackageSource(writer)

}