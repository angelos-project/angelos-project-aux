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

import org.angproj.aux.sec.SecureEntropy
import org.angproj.aux.util.readLongAt

public class EntropyRandom : AbstractBufferedRandom() {

    private val buffer = ByteArray(8)

    override val identifier: String
        get() = name

    override fun initialize() {
        buffer.fill(0)
        _instantiated = true
    }

    override fun finalize() {
        buffer.fill(0)
        _instantiated = false
    }

    override fun getRawLong(): Long {
        SecureEntropy.getEntropy(buffer)
        return buffer.readLongAt(0)
    }

    public companion object {
        public const val name: String = "EntropyRandom-SystemClock"
    }
}
