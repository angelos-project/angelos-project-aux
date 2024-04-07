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
package org.angproj.aux.rand

import org.angproj.aux.io.DataSize
import org.angproj.aux.util.floorMod
import kotlin.time.TimeSource
import kotlin.time.measureTime

/**
 * In order to get a balanced variety of ones and zeroes in clusters with minimum gaps
 * but individual patterns, the following four hexadecimals was chosen: 0x3, 0x5, 0xA and 0xC.
 *
 * All 4 hexadecimals gives a combination of two ones and two zeroes:
 *
 * 0x3 -> 0011
 * 0x5 -> 0101
 * 0xA -> 1010
 * 0xC -> 1100
 *
 * If the four hexadecimals are clustered in groups of four and everyone only appears once
 * there is a total 24 possible combinations: 35AC, 35CA, 3A5C, 3AC5, 3C5A, 3CA5, 53AC,
 * 53CA, 5A3C, 5AC3, 5C3A, 5CA3, A35C, A3C5, A53C, A5C3, AC35, AC53, C35A, C3A5, C53A, C5A3,
 * CA35, CA53.
 *
 * Each option repeating itself four times over to create a simple initialization vector with
 * an equal amount of ones and zeroes quite arbitrarily but not perfect. So that no hidden
 * weaknesses would be disguised, and hopefully no speculations of planted backdoors may occur.
 * */
public enum class InitializationVector(public val iv: Long) {
    IV_C3A5(0xC3A5C3A5C3A5C3A5uL.toLong()),
    IV_A53C(0xA53CA53CA53CA53CuL.toLong()),
    IV_CA35(0xCA35CA35CA35CA35uL.toLong()),
    IV_5C3A(0x5C3A5C3A5C3A5C3AuL.toLong()),
    IV_A5C3(0xA5C3A5C3A5C3A5C3uL.toLong()),
    IV_3A5C(0x3A5C3A5C3A5C3A5CuL.toLong()),
    IV_35AC(0x35AC35AC35AC35ACuL.toLong()),
    IV_A3C5(0xA3C5A3C5A3C5A3C5uL.toLong()),
    IV_AC35(0xAC35AC35AC35AC35uL.toLong()),
    IV_53AC(0x53AC53AC53AC53ACuL.toLong()),
    IV_C35A(0xC35AC35AC35AC35AuL.toLong()),
    IV_C5A3(0xC5A3C5A3C5A3C5A3uL.toLong()),
    IV_3CA5(0x3CA53CA53CA53CA5uL.toLong()),
    IV_5A3C(0x5A3C5A3C5A3C5A3CuL.toLong()),
    IV_35CA(0x35CA35CA35CA35CAuL.toLong()),
    IV_CA53(0xCA53CA53CA53CA53uL.toLong()),
    IV_5AC3(0x5AC35AC35AC35AC3uL.toLong()),
    IV_5CA3(0x5CA35CA35CA35CA3uL.toLong()),
    IV_AC53(0xAC53AC53AC53AC53uL.toLong()),
    IV_3C5A(0x3C5A3C5A3C5A3C5AuL.toLong()),
    IV_C53A(0xC53AC53AC53AC53AuL.toLong()),
    IV_53CA(0x53CA53CA53CA53CAuL.toLong()),
    IV_A35C(0xA35CA35CA35CA35CuL.toLong()),
    IV_3AC5(0x3AC53AC53AC53AC5uL.toLong());

    public companion object {

        private val moment = TimeSource.Monotonic.markNow()

        /**
         * Natural random based on fluctuations on nanosecond intervals which produces byte level entropy.
         * Actually costs precious processing time to generate, use sparsely.
         * Also comes close to Monte Carlo but not perfect!
         * */
        public fun realTimeGatedEntropy(data: ByteArray) {
            require(data.size <= DataSize._256B.size) { "To large for time-gated entropy! Max 256 bytes." }
            var entropy: Long = Long.MAX_VALUE
            var stub: Int = Int.MAX_VALUE
            data.indices.forEach {
                repeat(it.floorMod(16)) { stub += stub * Int.MAX_VALUE }
                entropy = moment.elapsedNow().inWholeNanoseconds * entropy.rotateLeft(32)
                data[it] = entropy.toByte()
            }
        }
    }
}