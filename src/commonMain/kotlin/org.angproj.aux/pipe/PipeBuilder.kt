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
import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.PumpWriter
import org.angproj.aux.mem.Default
import org.angproj.aux.mem.MemoryManager

public interface PipeBuilder {

    public abstract class Config {
        public var pipeMemMgr: MemoryManager<*> = Default
        public var pipeSegSize: DataSize = DataSize._2K
        public var pipeBufSize: DataSize = DataSize._8K
    }

    public class PullConfig(public val reader: PumpReader) : Config()
    public class PushConfig(public val writer: PumpWriter) : Config()

    public fun BuildSinkContext.pull(reader: PumpReader): PullConfig = PullConfig(reader)
    public fun BuildSourceContext.push(writer: PumpWriter): PushConfig = PushConfig(writer)


    public fun <C : Config> C.mem(ctx: MemoryManager<*>): C = this.also { pipeMemMgr = ctx }
    public fun <C : Config> C.buf(size: DataSize): C = this.also { pipeBufSize = size }
    public fun <C : Config> C.seg(size: DataSize): C = this.also { pipeSegSize = size }

    public fun PullConfig.utf(): GlyphSink = GlyphSink(PullPipe(pipeMemMgr, PumpSource(reader), pipeSegSize, pipeBufSize))
    public fun PullConfig.txt(): TextSink = TextSink(utf())
    public fun PullConfig.bin(): BinarySink = BinarySink(PullPipe(pipeMemMgr, PumpSource(reader), pipeSegSize, pipeBufSize))
    public fun PullConfig.pkg(): PackageSink = PackageSink(bin())

    public fun PushConfig.utf(): GlyphSource = GlyphSource(PushPipe(pipeMemMgr, PumpSink(writer), pipeSegSize, pipeBufSize))
    public fun PushConfig.txt(): TextSource = TextSource(utf())
    public fun PushConfig.bin(): BinarySource = BinarySource(PushPipe(pipeMemMgr, PumpSink(writer), pipeSegSize, pipeBufSize))
    public fun PushConfig.pkg(): PackageSource = PackageSource(bin())
}

public object BuildSinkContext : PipeBuilder
public object BuildSourceContext : PipeBuilder

public fun <T : PipeType, E: Sink<T>> buildSink(
    action: BuildSinkContext.() -> E
): E = BuildSinkContext.action()

public fun <T : PipeType, E: Source<T>> buildSource(
    action: BuildSourceContext.() -> E
): E = BuildSourceContext.action()

/* {
    public fun buildTextPullPipe(reader: PumpReader, memCtx: MemoryManager<*>): TextSink

    public fun buildBinaryPullPipe(reader: PumpReader, memCtx: MemoryManager<*>): BinarySink

    public fun buildPackagePullPipe(reader: PumpReader, memCtx: MemoryManager<*>): PackageSink

    public fun buildTextPushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): TextSource

    public fun buildBinaryPushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): BinarySource

    public fun buildPackagePushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): PackageSource
}*/