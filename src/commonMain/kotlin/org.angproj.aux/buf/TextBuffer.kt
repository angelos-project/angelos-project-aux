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

import org.angproj.aux.io.*
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.utf.Glyph.REPLACEMENT_CHARACTER
import org.angproj.aux.utf.Glyph.followOctetOf
import org.angproj.aux.utf.Glyph.readFollowData
import org.angproj.aux.utf.Glyph.startOctetOf
import org.angproj.aux.utf.SequenceType
import org.angproj.aux.util.Reify

public class TextBuffer private constructor(
    segment: Segment
): Buffer(segment), TextReadable, TextWritable {

    public constructor(size: Int) : this(Bytes(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun create(segment: Segment): TextBuffer = TextBuffer(segment)

    private val readOctet: () -> Byte = { segment.getByte(_position++) }

    private val writeOctet: (octet: Byte) -> Unit = { segment.setByte(_position++, it) }

    override fun readGlyph(): CodePoint {
        require(remaining >= 1) { "Buffer overflow, limit reached." }
        val octet = _segment.getByte(_position)
        val seqType = SequenceType.qualify(octet)
        require(remaining >= seqType.size) { "Buffer overflow, limit reached." }
        _position++
        return when(seqType) {
            SequenceType.FOLLOW_DATA, SequenceType.ILLEGAL -> REPLACEMENT_CHARACTER
            else -> readFollowData<Reify>(seqType, SequenceType.extract(seqType, octet), readOctet)
        }
    }

    override fun writeGlyph(codePoint: CodePoint): Int {
        val escPoint = codePoint.escapeInvalid()
        val seqType = escPoint.sectionTypeOf()
        require(remaining >= seqType.size) { "Buffer overflow, limit reached." }
        writeOctet(startOctetOf<Reify>(seqType, escPoint))
        repeat(seqType.size - 1) { writeOctet(followOctetOf<Reify>(seqType, escPoint, it + 1)) }
        return seqType.size
    }
}