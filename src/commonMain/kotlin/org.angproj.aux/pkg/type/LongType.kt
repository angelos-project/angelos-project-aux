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
package org.angproj.aux.pkg.type

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldType
import org.angproj.aux.pkg.Unfoldable
import org.angproj.io.buf.Retrievable
import kotlin.jvm.JvmInline

@JvmInline
public value class LongType(public val value: Long) : Enfoldable {
    override fun foldSize(foldType: FoldType): Long = Long.SIZE_BYTES.toLong()

    override fun enfold(outData: Storable, offset: Int): Long {
        outData.storeLong(offset, value)
        return foldSize(FoldType.BLOCK)
    }

    override fun enfold(outStream: Writable): Long {
        outStream.writeShort(Convention.LONG.type)
        outStream.writeLong(value)
        return foldSize(FoldType.STREAM) + 2
    }

    public companion object : Unfoldable<LongType> {
        override val foldType: FoldType = FoldType.BLOCK

        override fun unfold(inData: Retrievable, offset: Int): LongType = LongType(inData.retrieveLong(offset))

        override fun unfold(inStream: Readable): LongType {
            require(inStream.readShort() == Convention.LONG.type)
            return LongType(inStream.readLong())
        }
    }
}