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
import org.angproj.aux.mem.Default
import org.angproj.aux.mem.MemoryManager

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

    public fun buildTextSink(reader: PumpReader): TextSink = buildSink { pull(reader).txt() }
    public fun buildBinarySink(reader: PumpReader): BinarySink = buildSink { pull(reader).bin() }
    public fun buildPackageSink(reader: PumpReader): PackageSink = buildSink { pull(reader).pkg() }

    public fun buildTextSource(writer: PumpWriter): TextSource = buildSource { push(writer).txt() }
    public fun buildBinarySource(writer: PumpWriter): BinarySource = buildSource { push(writer).bin() }
    public fun buildPackageSource(writer: PumpWriter): PackageSource = buildSource { push(writer).pkg() }

    public fun buildTextPullPipe(reader: PumpReader): TextSink = buildTextPullPipe(reader, Default)
    public fun buildTextPullPipe(reader: PumpReader, memCtx: MemoryManager<*>): TextSink = PullPipe<TextType>(
        memCtx, PumpSource(reader)).getSink()

    public fun buildBinaryPullPipe(reader: PumpReader): BinarySink = buildBinaryPullPipe(reader, Default)
    public fun buildBinaryPullPipe(reader: PumpReader, memCtx: MemoryManager<*>): BinarySink = PullPipe<BinaryType>(
        memCtx, PumpSource(reader)).getSink()

    public fun buildPackagePullPipe(reader: PumpReader): PackageSink = buildPackagePullPipe(reader, Default)
    public fun buildPackagePullPipe(reader: PumpReader, memCtx: MemoryManager<*>): PackageSink = PackageSink(
        buildBinaryPullPipe(reader, memCtx))

    public fun buildTextPushPipe(writer: PumpWriter): TextSource = buildTextPushPipe(writer, Default)
    public fun buildTextPushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): TextSource = PushPipe<TextType>(
        memCtx, PumpSink(writer)).getSource()

    public fun buildBinaryPushPipe(writer: PumpWriter): BinarySource = buildBinaryPushPipe(writer, Default)
    public fun buildBinaryPushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): BinarySource = PushPipe<BinaryType>(
        memCtx, PumpSink(writer)).getSource()

    public fun buildPackagePushPipe(writer: PumpWriter): PackageSource = buildPackagePushPipe(writer, Default)
    public fun buildPackagePushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): PackageSource = PackageSource(
        buildBinaryPushPipe(writer, memCtx))
}