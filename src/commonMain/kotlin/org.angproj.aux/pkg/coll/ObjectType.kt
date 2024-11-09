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

import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.io.measureBytes
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline

@JvmInline
public value class ObjectType<P: Packageable>(public val value: P) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int =  when (foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> value.foldSize(foldFormat) + Enfoldable.OVERHEAD_LENGTH
    }

    public override fun enfoldStream(outStream: BinaryWritable): Int = outStream.measureBytes {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setLength(outStream, foldSize(FoldFormat.STREAM) - Enfoldable.OVERHEAD_LENGTH)
        value.enfold(outStream)
        Enfoldable.setEnd(outStream, conventionType)
    }.toInt()

    public companion object : Unfoldable<ObjectType<Packageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.OBJECT
        override val atomicSize: Int = 0

        public fun <P: Packageable> unfoldStream(
            inStream: BinaryReadable, unpack: () -> P
        ): ObjectType<P> {
            require(Unfoldable.getType(inStream, conventionType))
            val obj = inStream.measureBytes(Unfoldable.getLength(inStream)) {
                ObjectType(unpack().also { o -> o.unfold(inStream) })
            }
            require(Unfoldable.getEnd(inStream, conventionType))
            return obj
        }
    }
}