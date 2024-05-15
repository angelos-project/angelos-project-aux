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
package org.angproj.aux.util

import org.angproj.aux.codec.Decoder
import org.angproj.aux.codec.Encoder
import org.angproj.aux.utf.UtfString

public object Unicode: Encoder<ByteArray, UtfString>, Decoder<UtfString, ByteArray> {

    /*public fun sanitizeWithEscaping(data: ByteArray): ByteArray {
        val fsm = Sanitizer.create()
        fsm.state = Sanitizer.SIX_COMPLETED
    }*/

    override fun decode(data: UtfString): ByteArray {
        TODO("Not yet implemented")
    }

    override fun encode(data: ByteArray): UtfString {
        TODO("Not yet implemented")
    }
}