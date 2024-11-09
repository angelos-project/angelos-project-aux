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

public interface ByteCount {
    public val count: Long
}

public inline fun <reified T: ByteCount, E: Any> T.measureBytes(expected: Int, block: Int.() -> E): E {
    val curCnt = count
    return expected.block().also {
        check((count - curCnt).toInt() == expected) {
            "Expected $expected bytes but ${count - curCnt} was read"
        }
    }
}

public inline fun <reified T: ByteCount> T.measureBytes(block: () -> Unit): Int {
    val curCnt = count
    block()
    return (count - curCnt).toInt()
}