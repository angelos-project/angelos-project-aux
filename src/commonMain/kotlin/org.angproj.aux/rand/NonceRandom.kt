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

import org.angproj.aux.util.Epoch
import org.angproj.aux.util.Random
import org.angproj.aux.util.floorMod
import kotlin.math.max

public class NonceRandom(private var seederHandle: Int) : AbstractBufferedRandom() {

    private var seedPos = 0

    private var counter: Long = 1
    private var reseed: Long = 0
    private var entropy: Long = 0
    private var nlfMask: Long = 0
    private var s0: Long = 0
    private var s1: Long = 0
    private var s2: Long = 0
    private var s3: Long = 0

    override val identifier: String
        get() = name

    override fun initialize() {
        require(seederHandle != 0) { "No entropy source found." }

        reseed = Epoch.getEpochMilliSecs()

        entropy = Random.receive(seederHandle).getLong()
        repeat(16) { round() }
        _instantiated = true
    }

    override fun finalize() {
        seederHandle = 0
        seedPos = 0
        counter = 1
        reseed = 0
        entropy = 0
        nlfMask = 0
        s0 = 0
        s1 = 0
        s2 = 0
        s3 = 0
        _instantiated = false
    }

    private fun round() {

        if (counter.mod(10_000) == 0) {
            val seed = Epoch.getEpochMilliSecs()
            if (seed != reseed) {
                entropy = -(Random.receive(seederHandle).getLong() - seed).rotateRight(2) xor
                        (entropy + reseed).inv().rotateLeft(17) * counter
                reseed = seed
            }
        }

        nlfMask = (entropy and nlfMask and s0) xor ((s1 and s2) * 2) xor (s3 * 4)

        val temp = -(s1 - entropy).rotateRight(2) xor (s1 + counter).inv().rotateLeft(17)
        s1 = -(s2 + entropy).rotateRight(2) xor (s2 - counter).inv().rotateLeft(17)
        s2 = -(s3 - counter).rotateRight(2) xor (s3 + entropy).inv().rotateLeft(17)
        s3 = -(s0 + counter).rotateRight(2) xor (s0 - entropy).inv().rotateLeft(17)
        s0 = temp

        counter = max(1, counter + 1)
    }

    override fun getRawLong(): Long = when (seedPos.floorMod(4)) {
        0 -> s0
        1 -> s1
        2 -> s2
        else -> s3.also { round() }
    } xor nlfMask.also { seedPos++ }

    public companion object {
        public const val name: String = "NonceRandom-Standard"
    }
}
