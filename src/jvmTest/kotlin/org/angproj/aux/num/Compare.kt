/**
 * Copyright (c) 2023-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.num

internal operator fun BigInt.compareTo(other: BigMath<*>): Int = compareSpecial(other).state

internal fun BigInt.compareSpecial(other: BigMath<*>): BigCompare = when {
    sigNum.state > other.sigNum.state -> BigCompare.GREATER
    sigNum.state < other.sigNum.state -> BigCompare.LESSER
    sigNum == BigSigned.POSITIVE -> MutableBigInt.compareMagnitude(this, other)
    sigNum == BigSigned.NEGATIVE -> MutableBigInt.compareMagnitude(other, this)
    else -> BigCompare.EQUAL
}

internal fun MutableBigInt.Companion.compareMagnitude(left: BigMath<*>, right: BigMath<*>): BigCompare = when {
    left.mag.size < right.mag.size -> BigCompare.LESSER
    left.mag.size > right.mag.size -> BigCompare.GREATER
    else -> {
        left.mag.indices.forEach { idx ->
            val xNum = left.mag[idx] // Should NOT use getL()
            val yNum = right.mag[idx]
            if (xNum != yNum) return@compareMagnitude if (xNum xor -0x80000000 < yNum xor -0x80000000
            ) BigCompare.LESSER else BigCompare.GREATER
        }
        BigCompare.EQUAL
    }
}