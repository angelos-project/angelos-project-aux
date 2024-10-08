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

import kotlin.test.Test

abstract class AbstractBufferTest {

    @Test
    abstract fun getSegment()

    @Test
    abstract fun getCapacity()

    @Test
    abstract fun getPosition()

    @Test
    abstract fun positionAt()

    @Test
    abstract fun getLimit()

    @Test
    abstract fun limitAt()

    @Test
    abstract fun getMark()

    @Test
    abstract fun markAt()

    @Test
    abstract fun reset()

    @Test
    abstract fun clear()

    @Test
    abstract fun flip()

    @Test
    abstract fun rewind()

    @Test
    abstract fun getRemaining()

    @Test
    abstract fun isView()

    @Test
    abstract fun isMem()

    @Test
    abstract fun close()
}