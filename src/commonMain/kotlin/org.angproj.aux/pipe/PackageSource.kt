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

import org.angproj.aux.io.PackageWritable
import org.angproj.aux.pkg.Enfoldable

public class PackageSource(
    private val src: BinarySource
): Source, PackageType, PackageWritable {
    override fun flush(): Unit = src.flush()

    override fun isOpen(): Boolean = src.isOpen()

    override fun close(): Unit = src.close()

    override fun <S : Enfoldable> writePackage(pkg: S) {
        TODO("Not yet implemented")
    }
}