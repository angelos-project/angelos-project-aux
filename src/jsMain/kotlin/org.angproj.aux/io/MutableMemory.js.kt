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
package org.angproj.aux.io

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class MutableMemory actual constructor(size: Int) : Memory(size), MutableSegment {

    actual override fun setByte(index: Int, value: Byte) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setShort(index: Int, value: Short) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setInt(index: Int, value: Int) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setLong(index: Int, value: Long) {
        throw UnsupportedOperationException("No access to native memory.")
    }
}