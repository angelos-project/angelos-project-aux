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

public interface PipeBuilder {
    public fun buildTextPullPipe(reader: PumpReader): TextSink

    public fun buildBinaryPullPipe(reader: PumpReader): BinarySink

    public fun buildPackagePullPipe(reader: PumpReader): PackageSink

    public fun buildTextPushPipe(writer: PumpWriter): TextSource

    public fun buildBinaryPushPipe(writer: PumpWriter): BinarySource

    public fun buildPackagePushPipe(writer: PumpWriter): PackageSource
}