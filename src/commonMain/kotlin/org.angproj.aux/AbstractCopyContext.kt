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
package org.angproj.aux

import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify


public interface CopySource {
    public fun get8(): (pos: Int) -> Byte
    public fun get64(): (pos: Int) -> Long
}

public interface CopyDestination {
    public fun set8(): (pos: Int, value: Byte) -> Unit
    public fun set64(): (pos: Int, value: Long) -> Unit
}

public abstract class Copy: CopySource, CopyDestination {

    private inline fun <reified R: Reifiable> build64(): (Int) -> Unit {
        val s64 = set64()
        val g64 = get64()
        return { it: Int -> s64(it, g64(it)) }
    }

    private inline fun <reified R: Reifiable> build8(): (Int) -> Unit {
        val s8 = set8()
        val g8 = get8()
        return { it: Int -> s8(it, g8(it)) }
    }

    protected inline fun <reified R: Reifiable> chunkLoop(
        index: Int, length: Int, slice: Int, action: (Int) -> Unit
    ): Int {
        val steps = (length - index) / slice
        val size = steps * slice
        if (steps > 0) (index until (index + size) step slice).forEach { action(it) }
        return index + size
    }

    protected inline fun <reified R: Reifiable> innerCopy(
        start: Int, stop: Int, c64: (Int) -> Unit, c8: (Int) -> Unit
    ): Int {
        val index = chunkLoop<Reify>(start, stop, 8, c64)
        return chunkLoop<Reify>(index, stop, 1, c8)
    }

    public operator fun invoke(
        start: Int, stop: Int
    ): Int {
        return innerCopy<Reify>(start, stop, build64<Reify>(), build8<Reify>()) - start
    }
}

public class Adam {

    protected fun readLong(pos: Int) : Long = 34

    internal interface Source: CopySource {
        override fun get8(): (Int) -> Byte = { 0x01 }

        override fun get64(): (pos: Int) -> Long = { this.readLong(pos) }
    }
}

public class Bengt {
    internal interface Destination: CopyDestination {
        override fun set8(): (Int, Byte) -> Unit = { _: Int, _: Byte -> }

        override fun set64(): (pos: Int, value: Long) -> Unit = { _: Int, _: Long -> }
    }
}

public fun doCopy() {

    val cpyCtx = object: Copy(), Adam.Source, Bengt.Destination
}

public object Subject: