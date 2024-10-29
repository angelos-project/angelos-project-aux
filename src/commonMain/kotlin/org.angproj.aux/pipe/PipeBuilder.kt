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
import org.angproj.aux.mem.MemoryManager

public interface PipeBuilder {
    public fun buildTextPullPipe(reader: PumpReader, memCtx: MemoryManager<*>): TextSink

    public fun buildBinaryPullPipe(reader: PumpReader, memCtx: MemoryManager<*>): BinarySink

    public fun buildPackagePullPipe(reader: PumpReader, memCtx: MemoryManager<*>): PackageSink

    public fun buildTextPushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): TextSource

    public fun buildBinaryPushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): BinarySource

    public fun buildPackagePushPipe(writer: PumpWriter, memCtx: MemoryManager<*>): PackageSource
}