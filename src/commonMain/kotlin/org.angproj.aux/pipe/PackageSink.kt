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
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.Unfoldable

public class PackageSink(
    private val sink: BinarySink
): Sink, PackageType, PackageReadable {
    override fun isOpen(): Boolean = sink.isOpen()

    override fun close(): Unit = sink.close()

    override fun<E : Enfoldable, S: Unfoldable<E>> readPackage(): S {
        TODO("Not yet implemented")
    }
}