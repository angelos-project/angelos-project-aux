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

import org.angproj.aux.io.PackageReadable
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType

public class PackageSink(
    private val sink: BinarySink
): Sink<PackageType>, PackageReadable {

    public constructor(pipe: PullPipe) : this(BinarySink(pipe))

    override val count: Long
        get() = sink.count

    override fun isOpen(): Boolean = sink.isOpen()

    override fun close(): Unit = sink.close()

    override fun <P : Package> readObject(action: () -> P): P {
        return ObjectType.unfoldStream(sink, action).value
    }

    override fun <P : Packageable> readStruct(action: () -> P): P {
        return StructType.unfoldStream(sink, action).value
    }
}