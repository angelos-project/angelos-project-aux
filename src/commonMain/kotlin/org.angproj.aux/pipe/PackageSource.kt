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
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType

public class PackageSource(
    private val src: BinarySource
): Source, PackageType, PackageWritable {

    override val count: Long
        get() = src.count

    public fun flush(): Unit = src.flush()

    override fun isOpen(): Boolean = src.isOpen()

    override fun close(): Unit = src.close()

    override fun <P : Package> writeObject(pkg: P): Int = ObjectType(pkg).enfoldStream(src)

    override fun <P : Packageable> writeStruct(pkg: P): Int = StructType(pkg).enfoldStream(src)
}