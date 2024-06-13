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

/**
 * Default Pipe builder using the default memory manager which works as failsafe.
 * */
public object Pipe: PipeBuilder {
    override fun buildTextPullPipe(reader: PumpReader): TextSink = PullPipe<TextType>(
        Default, PumpSource(reader)).getSink()

    override fun buildBinaryPullPipe(reader: PumpReader): BinarySink = PullPipe<BinaryType>(
        Default, PumpSource(reader)).getSink()

    override fun buildPackagePullPipe(reader: PumpReader): PackageSink = PackageSink(
        buildBinaryPullPipe(reader))

    override fun buildTextPushPipe(writer: PumpWriter): TextSource = PushPipe<TextType>(
        Default, PumpSink(writer)).getSource()

    override fun buildBinaryPushPipe(writer: PumpWriter): BinarySource = PushPipe<BinaryType>(
        Default, PumpSink(writer)).getSource()

    override fun buildPackagePushPipe(writer: PumpWriter): PackageSource = PackageSource(
        buildBinaryPushPipe(writer))
}