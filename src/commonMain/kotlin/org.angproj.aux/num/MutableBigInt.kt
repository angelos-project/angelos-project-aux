/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.aux.util.mutableBigIntOf

public class MutableBigInt internal constructor(magnitude: MutableList<Int>, sigNum: BigSigned) :
    AbstractBigInt<MutableList<Int>>(magnitude, sigNum) {

    public constructor(magnitude: IntArray, sigNum: BigSigned) : this(
        magnitude.toMutableList(),
        sigNumZeroAdjust(magnitude, sigNum)
    )

    public fun setIdx(index: Int, num: Int): Unit = mag.revSet(index, num)

    public fun setIdxL(index: Int, num: Long): Unit = setIdx(index, num.toInt())

    public fun setUnreversedIdx(index: Int, num: Int) {
        mag[index] = num
    }

    public fun setUnreversedIdxL(index: Int, num: Long): Unit = setUnreversedIdx(index, num.toInt())

    override fun negate(): MutableBigInt = MutableBigInt(mag, sigNum.negate())

    override fun copyOf(): MutableBigInt = MutableBigInt(mag, sigNum)
    override fun of(value: IntArray): MutableBigInt = mutableBigIntOf(value)
    override fun of(value: IntArray, sigNum: BigSigned): MutableBigInt = MutableBigInt(value, sigNum)
    override fun of(value: Long): MutableBigInt = mutableBigIntOf(value)

    public override fun toBigInt(): BigInt = BigInt(mag.toIntArray(), sigNum)
    public override fun toMutableBigInt(): MutableBigInt = this

    public companion object {
        public val two: MutableBigInt by lazy { MutableBigInt(intArrayOf(2), BigSigned.POSITIVE) }
        public val one: MutableBigInt by lazy { MutableBigInt(intArrayOf(1), BigSigned.POSITIVE) }
        public val zero: MutableBigInt by lazy { MutableBigInt(intArrayOf(0), BigSigned.ZERO) }
        public val minusOne: MutableBigInt by lazy { MutableBigInt(intArrayOf(1), BigSigned.NEGATIVE) }
    }
}

public fun emptyMutableBigIntOf(value: IntArray = intArrayOf(0)): MutableBigInt =
    MutableBigInt(value, BigSigned.POSITIVE)
