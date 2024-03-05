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
package org.angproj.aux.pkg.arb

import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline

@JvmInline
public value class StructType<P: BlockPackageable>(public val value: P) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = value.foldSize(foldFormat)

    public override fun enfold(outData: Storable, offset: Int): Long {
        value.enfold(outData, offset)
        return foldSize(FoldFormat.BLOCK)
    }

    public companion object : Unfoldable<StructType<BlockPackageable>> {
        override val foldFormatSupport: FoldFormat = FoldFormat.BLOCK

        public fun unfold(
            inData: Retrievable, offset: Int, unpack: (Retrievable, Int) -> BlockPackageable
        ) : StructType<BlockPackageable> = StructType(unpack(inData, offset))
    }
}