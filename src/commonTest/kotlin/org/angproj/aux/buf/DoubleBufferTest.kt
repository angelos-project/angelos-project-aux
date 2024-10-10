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
package org.angproj.aux.buf

import org.angproj.aux.TestInformationStub


class DoubleBufferTest: AbstractArrayBufferTest<Double>() {

    override val refValue: Double = TestInformationStub.refDouble

    override fun setInput(): DoubleBuffer {
        val lb = DoubleBuffer(capValue)
        (0 until lb.limit).forEach {
            lb[it] = refValue
        }
        return lb
    }
}