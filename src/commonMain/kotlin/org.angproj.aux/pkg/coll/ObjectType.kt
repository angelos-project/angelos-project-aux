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
public value class ObjectType<P: Packageable>(public val value: P) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = value.foldSize(foldFormat) + Enfoldable.OVERHEAD_LENGTH

    public fun enfoldToStream(outStream: Writable): Long {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setLength(outStream, foldSize(FoldFormat.STREAM) - Enfoldable.OVERHEAD_LENGTH)
        value.enfold(outStream)
        Enfoldable.setEnd(outStream, conventionType)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<ObjectType<Packageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.OBJECT
        override val atomicSize: Int = 0

        @Suppress("UNCHECKED_CAST")
        public fun <P: Packageable, O: ObjectType<P>> unfoldFromStream(
            inStream: Readable, unpack: () -> P
        ): O {
            require(Unfoldable.getType(inStream, conventionType))
            val length = Unfoldable.getLength(inStream)
            val obj = ObjectType(unpack().also { o -> o.unfold(inStream) })
            require(Unfoldable.getEnd(inStream, conventionType))
            return obj as O
        }
    }
}