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

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.Copyable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Copyable, Cleanable {

    public override fun dispose() {}

    override val limit: Int = size

    override fun getLong(index: Int): Long = throw UnsupportedOperationException("No access to native memory")

    override fun getByte(index: Int): Byte = throw UnsupportedOperationException("No access to native memory")

    override fun setLong(index: Int, value: Long): Unit = throw UnsupportedOperationException("No access to native memory")

    override fun setByte(index: Int, value: Byte): Unit = throw UnsupportedOperationException("No access to native memory")
}

public actual fun allocateMemory(size: Int): Memory {
    validateAskedMemorySize(size)
    throw UnsupportedOperationException("No access to native memory")
}

/**
 * void speedmemcpy(void *dest, const void * src, uint32_t n) {
 *     uint32_t big = n / sizeof(uint64_t);
 *     uint32_t small = n % sizeof(uint64_t);
 *
 *     uint64_t *dest_big = (uint64_t *) dest;
 *     uint64_t *src_big = (uint64_t *) src;
 *
 *     for (uint32_t i = 0; i < big; i++) {
 *         dest_big[i] = src_big[i];
 *     }
 *
 *     char *dest_small = (char *) dest + big * sizeof(uint64_t);
 *     char *src_small = (char *) src + big * sizeof(uint64_t);
 *     for (uint32_t j = 0; j < small; j++) {
 *         dest_small[j] = src_small[j];
 *     }
 * }
 * */
@PublishedApi
internal actual fun speedMemCpy(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long): Int {
    throw UnsupportedOperationException("No access to native memory")

    /*val src: Memory = Memory(DataSize._1K.size, 0)
    val dst: Memory = Memory(DataSize._1K.size, 0)

    val tot = idxFrom - idxTo
    val big = tot / TypeSize.long
    val small = tot.mod(TypeSize.long)

    val dstBig = dst.ptr + dstOff
    val srcBig = src.ptr + idxFrom

    var idx = 0
    while(idx < big) {
        dst.setLong(idx + dstBig, src.getLong(idx + srcBig))
        idx += TypeSize.long
    }

    while (idx++ < tot) dst.setByte(idx + dstBig, src.getByte(idx + srcBig))*/
}