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
import org.angproj.aux.buf.isNull
import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Unfoldable


public interface ArrayUnfoldable<G, F: ArrayBuffer<G>, E : ArrayEnfoldable<G, F>>: Unfoldable<E> {

    public val factory: (count: Int) -> F

    public override fun unfoldStream(inStream: BinaryReadable): E

    public fun unfoldFromStream(inStream: BinaryReadable, value: F)

    public companion object {

        internal fun <G, F: ArrayBuffer<G>>arrayUnfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            array: ArrayBuffer<G>,
            atomicSize: Int,
            retrieve: (inData: Retrievable, index: Int) -> G
        ): Int = with(array) {
            require(!array.isNull()) { "Null Array!" }
            indices.forEach { this[it] = retrieve(inData, offset + it * atomicSize) }
            return (limit * atomicSize)
        }

        /*internal fun <G, F: ArrayBuffer<G>, E : ArrayEnfoldable<G, F>>arrayUnfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int,
            atomicSize: Int,
            factory: (Int) -> E,
            retrieve: (inData: Retrievable, index :Int) -> G
        ): E = factory(count).apply {
            with(this.value){
                indices.forEach { this[it] = retrieve(inData, offset + it * atomicSize) }
            }
        }*/

        internal fun <G, F: ArrayBuffer<G>, E : ArrayEnfoldable<G, F>>arrayUnfoldFromStream(
            inStream: BinaryReadable,
            conventionType: Convention,
            factory: (Int) -> F,
            stream: (inStream: BinaryReadable) -> G
        ): F {
            require(Unfoldable.getType(inStream, conventionType))
            return factory(Unfoldable.getCount(inStream)).apply {
                with(this) {
                    (0 until limit).forEach { set(it, stream(inStream)) }
                }
                require(Unfoldable.getEnd(inStream, conventionType))
            }
        }

        internal fun <G, F: ArrayBuffer<G>, E : ArrayEnfoldable<G, F>>arrayUnfoldFromStream(
            inStream: BinaryReadable,
            conventionType: Convention,
            array: F,
            stream: (inStream: BinaryReadable) -> G
        ) {
            //require(!array.isNull()) { "Null Array!" }
            require(Unfoldable.getType(inStream, conventionType))
            require(Unfoldable.getCount(inStream) == array.limit)
            with(array) { indices.forEach { set(it, stream(inStream)) } }
            require(Unfoldable.getEnd(inStream, conventionType))
        }
    }
}