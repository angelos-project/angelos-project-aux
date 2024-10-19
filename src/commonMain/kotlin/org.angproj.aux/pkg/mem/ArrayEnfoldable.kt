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
package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.ArrayBuffer
import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.io.Storable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat


public interface ArrayEnfoldable<G, E: ArrayBuffer<G>>: Enfoldable {

    public val value: E

    public companion object{
        public fun <G, E: ArrayBuffer<G>> arrayFoldSize(
            value: E,
            atomicSize: Int,
            foldFormat: FoldFormat
        ): Long = when (foldFormat) {
            FoldFormat.BLOCK -> (atomicSize * value.limit).toLong()
            FoldFormat.STREAM -> (atomicSize * value.limit).toLong() + Enfoldable.OVERHEAD_COUNT
        }

        public fun <G, E: ArrayBuffer<G>>arrayEnfoldToBlock(
            value: E,
            atomicSize: Int,
            outData: Storable,
            offset: Int = 0,
            store: (outData: Storable, index: Int, v: G) -> Unit
        ): Long {
            value.forEachIndexed { index, v -> store(outData,offset + index * atomicSize, v) }
            return arrayFoldSize(value, atomicSize, FoldFormat.BLOCK)
        }

        public fun <G, E: ArrayBuffer<G>>arrayEnfoldToStream(
            value: E,
            atomicSize: Int,
            conventionType: Convention,
            outStream: BinaryWritable,
            stream: (outStream: BinaryWritable, v: G) -> Unit
        ): Long {
            Enfoldable.setType(outStream, conventionType)
            Enfoldable.setCount(outStream, value.limit)
            value.forEach { stream(outStream, it) }
            Enfoldable.setEnd(outStream, conventionType)
            return arrayFoldSize(value, atomicSize, FoldFormat.STREAM)
        }
    }
}