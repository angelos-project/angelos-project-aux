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
package org.angproj.aux.pipe

import org.angproj.aux.buf.Pump
import org.angproj.aux.io.PumpWriter
import org.angproj.aux.io.TextReadable
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.utf.Glyph
import org.angproj.aux.util.Reify

public class TextSink(
    pump: PumpWriter = Pump
): AbstractSink<TextType>(pump), TextType, TextReadable {

    /*private inline fun <reified : Reifiable>readAll(readOctet: () -> Byte): CodePoint {
        val octet = readOctet()
        return when(val seqType = SequenceType.qualify(octet)) {
            SequenceType.FOLLOW_DATA, SequenceType.ILLEGAL -> Glyph.REPLACEMENT_CHARACTER
            else -> readFollowData<Reify>(seqType, SequenceType.extract(seqType, octet), readOctet)
        }
    }

    private inline fun <reified : Reifiable> readFollowData(seq: SequenceType, codePoint: Int, readOctet: () -> Byte): CodePoint {
        var value = codePoint
        repeat(seq.size - 1) {
            val octet = readOctet()
            if(SequenceType.qualify(octet) != SequenceType.FOLLOW_DATA) return Glyph.REPLACEMENT_CHARACTER
            value = (value shl seq.bits) or (SequenceType.extract(SequenceType.FOLLOW_DATA, octet))
        }
        return CodePoint(value)
    }*/

    override fun readGlyph(): CodePoint = Glyph.readStart<Reify> {
        if(pos == seg.limit) pullSegment()
        seg.getByte(pos).also { pos++ }
    }
}