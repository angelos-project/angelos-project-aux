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
import org.angproj.aux.pkg.BlockPackageable
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import kotlin.jvm.JvmInline

@JvmInline
public value class StructType<P: BlockPackageable>(public val value: P) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long {
        TODO("Not yet implemented")
    }

    public override fun enfold(outData: Storable, offset: Int): Long {

    }

    public companion object : Unfoldable<StructType<BlockPackageable>> {
        override val foldFormatSupport: FoldFormat
            get() = TODO("Not yet implemented")

        public override fun unfold(inData: Retrievable, offset: Int) : StructType<BlockPackageable> { throw UnsupportedOperationException() }

    }
}