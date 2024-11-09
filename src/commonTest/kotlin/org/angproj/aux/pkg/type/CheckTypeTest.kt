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
package org.angproj.aux.pkg.type

import org.angproj.aux.buf.asBinary
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.sec.SecureRandom
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CheckTypeTest {

    @Test
    fun enfoldUnfoldToStream() {
        val key = SecureRandom.readLong()
        val bin = binOf(DataSize._1K.size).apply { securelyRandomize() }
        val buf = BufMgr.binary(DataSize._2K.size)

        val b1 = BlockType(bin)
        val len = b1.enfoldToStreamByCheck(buf, key)
        buf.flip()

        val b2 = BlockType.unfoldFromStreamByCheck(buf, key)

        assertEquals(b1, b2)
        assertEquals(len, buf.limit)

        buf.clear()
        b1.enfoldToStreamByCheck(buf, key)
        buf.asBinary().storeInt(124, SecureRandom.readInt())
        buf.flip()

        assertFailsWith<IllegalStateException> { BlockType.unfoldFromStreamByCheck(buf, key) }
    }
}