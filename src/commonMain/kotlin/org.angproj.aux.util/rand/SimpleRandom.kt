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
package org.angproj.aux.util.rand

import org.angproj.aux.util.Random

public class SimpleRandom(private var seederHandle: Int = 0) : AbstractBufferedRandom() {

    public override val identifier: String
        get() = name

    override fun initialize() {
        require(seederHandle != 0) { "No entropy source found." }

        seed = Random.receive(seederHandle).getLong()
        _instantiated = true
    }

    override fun finalize() {
        _instantiated = false
        counter = 0
        seed = 0
    }

    private var counter: Long = 0
    private var seed: Long = 0

    override fun getRawLong(): Long {
        counter++
        seed = -(seed - counter).rotateRight(2) xor (seed + counter).inv().rotateLeft(17)
        return seed
    }

    public companion object {
        public const val name: String = "SimpleRandom-Stupid"
    }
}