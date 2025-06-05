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
package org.angproj.aux.res

import kotlinx.cinterop.*
import platform.posix.*
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.Copyable
import org.angproj.aux.util.floorMod

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Copyable, Cleanable {

    public actual override fun dispose() {
        nativeHeap.free(ptr.toCPointer<ByteVar>()!!)
    }

    actual override val limit: Int = size

    private inline fun <reified T: Number> speedLongGet(index: Int): Long = (ptr + index).toCPointer<LongVar>()!!.pointed.value
    private inline fun <reified T: Number> speedLongSet(index: Int, value: Long) { (ptr + index).toCPointer<LongVar>()!!.pointed.value = value }
    private inline fun <reified T: Number> speedByteGet(index: Int): Byte = (ptr + index).toCPointer<ByteVar>()!!.pointed.value
    private inline fun <reified T: Number> speedByteSet(index: Int, value: Byte) { (ptr + index).toCPointer<ByteVar>()!!.pointed.value = value }

    actual override fun getLong(index: Int): Long = speedLongGet<Int>(index)

    actual override fun getByte(index: Int): Byte = speedByteGet<Int>(index)

    actual override fun setLong(index: Int, value: Long): Unit = speedLongSet<Int>(index, value)

    actual override fun setByte(index: Int, value: Byte): Unit = speedByteSet<Int>(index, value)
}

@OptIn(ExperimentalForeignApi::class)
public actual fun allocateMemory(size: Int): Memory {
    validateAskedMemorySize(size)
    return Memory(size, nativeHeap.allocArray<ByteVar>(size).toLong())
}

@PublishedApi
internal actual fun speedMemCpy(
    idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long
): Int = speedMemCpyAddress3<Unit>(idxFrom, idxTo, dstOff, src, dst)

@OptIn(ExperimentalForeignApi::class)
internal inline fun <reified E: Any>speedMemCpyAddress(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long): Int = memScoped {
    val tot = idxFrom - idxTo
    val long = tot - tot.floorMod(TypeSize.long)

    val dstPtr = dst + dstOff
    val srcPtr = src + idxFrom

    var idx = 0
    while(idx < long) {
        (idx + dstPtr).toCPointer<LongVar>()!!.pointed.value = (idx + srcPtr).toCPointer<LongVar>()!!.pointed.value
        idx += TypeSize.long
    }

    while (idx < tot) {
        (idx + dstPtr).toCPointer<ByteVar>()!!.pointed.value = (idx + srcPtr).toCPointer<ByteVar>()!!.pointed.value
        idx++
    }
    return tot
}


/**
 * memcpy not implemented the same at 32-bit targets
 * */
/*@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
internal inline fun <reified E: Any>speedMemCpyAddress2(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long): Int = memScoped {
    val tot = idxFrom - idxTo
    //val long = tot - tot.floorMod(TypeSize.long)

    val dstPtr: Long = dst + dstOff
    val srcPtr: Long = src + idxFrom

    //memcpy(srcPtr.toCPointer<LongVar>(), dstPtr.toCPointer<LongVar>(), long.toULong())
    memcpy(
        dstPtr.toCPointer<CArrayPointerVar<ByteVar>>(),
        srcPtr.toCPointer<CArrayPointerVar<ByteVar>>(),
        tot.convert()
    )


    var idx = long
    while (idx < tot) {
        (idx + dstPtr).toCPointer<ByteVar>()!!.pointed.value = (idx + srcPtr).toCPointer<ByteVar>()!!.pointed.value
        idx++
    }
    return tot
}*/

@OptIn(ExperimentalForeignApi::class)
internal inline fun <reified E: Any>speedMemCpyAddress3(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long): Int = memScoped {
    val tot = idxFrom - idxTo
    val long = tot - tot.floorMod(TypeSize.long)
    val steps = long / TypeSize.long

    val dstLongPtr: CPointer<LongVar> = (dst + dstOff).toCPointer()!! //.toCPointer<CArrayPointerVar<Long>>()!!.asStableRef<Long>()
    val srcLongPtr: CPointer<LongVar> = (src + idxFrom).toCPointer()!!

    srcLongPtr.usePinned {
        dstLongPtr.usePinned {
            var idx = 0
            while(idx < steps) {
                dstLongPtr[idx] = srcLongPtr[idx]
                idx++
            }
        }
    }

    val dstBytePtr: CPointer<ByteVar> = (dst + dstOff + long).toCPointer()!!
    val srcBytePtr: CPointer<ByteVar> = (src + idxFrom + long).toCPointer()!!

    srcBytePtr.usePinned {
        dstBytePtr.usePinned {
            var idx = long
            while (idx < tot) {
                dstBytePtr[idx] = srcBytePtr[idx]
                idx++
            }
        }
    }
    return tot
}