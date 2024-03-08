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
package org.angproj.aux.pkg.coll

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline

@JvmInline
public value class DictType<P: Packageable>(public val value: Map<String, P>) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = TODO("Not yet implemented")

    public fun enfoldToStream(outStream: Writable): Long {
        TODO("Not yet implemented")
    }

    public companion object : Unfoldable<DictType<StreamPackageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.DICT
        override val atomicSize: Int = 0

        public fun unfoldFromStream(
            inStream: Readable, unpack: (Readable) -> Packageable
        ): DictType<StreamPackageable> {
            TODO("Not yet implemented")
        }
    }
}