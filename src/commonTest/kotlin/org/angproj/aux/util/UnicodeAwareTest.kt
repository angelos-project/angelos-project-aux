/**
 * Copyright (c) 2022-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.aux.TestInformationStub.chineseLipsum
import org.angproj.aux.TestInformationStub.greekLipsum
import org.angproj.aux.TestInformationStub.latinLipsum
import kotlin.test.*


class StreamStub(val buffer: ByteArray) {

    constructor(len: Int): this(ByteArray(len))

    var position: Int = 0

    fun readGlyph(): CodePoint = withUnicodeAware {
        readGlyphStrm { buffer[position++] }
    }

    fun writeGlyph(codePoint: CodePoint): Int = withUnicodeAware {
        writeGlyphStrm(codePoint) { buffer[position++] = it }
    }
}


class BlockStub(val buffer: ByteArray) {

    constructor(len: Int): this(ByteArray(len))

    fun readGlyphAt(offset: Int): CodePoint = withUnicodeAware(buffer) {
        var pos = offset
        return readGlyphBlk(buffer.size - pos) { it[pos++] }
    }

    fun writeGlyphAt(offset: Int, value: CodePoint): Int = withUnicodeAware(buffer) {
        var pos = offset
        return writeGlyphBlk(value, buffer.size - pos) { it2 -> it[pos++] = it2 }
    }
}


class CodePointTest {

    @Test
    fun isValid() = withUnicodeAware {
        assertTrue { 0x10.toCodePoint().isValid() }
        assertTrue { 0x100.toCodePoint().isValid() }
        assertTrue { 0x1000.toCodePoint().isValid() }
        assertTrue { 0x10000.toCodePoint().isValid() }
        assertFalse { (-0x1).toCodePoint().isValid() }
        assertFalse { 0xDAFF.toCodePoint().isValid() }
        assertFalse { 0x110000.toCodePoint().isValid() }
    }

    @Test
    fun octetSize() = withUnicodeAware {
        assertEquals(0x10.toCodePoint().octetSize(), 1)
        assertEquals(0x100.toCodePoint().octetSize(), 2)
        assertEquals(0x1000.toCodePoint().octetSize(), 3)
        assertEquals(0x10000.toCodePoint().octetSize(), 4)
        assertEquals((-0x1).toCodePoint().octetSize(), -1)
        assertEquals(0xDAFF.toCodePoint().octetSize(), -1)
        assertEquals(0x110000.toCodePoint().octetSize(), -1)
    }
}


class UnicodeAwareTest {

    val phrase = "Ἐπειδὴ"

    // Latin Capital Letter A
    private val ascii = Pair(
        // Code
        byteArrayOf(0x41),
        // Entity
        0x41.toInt()
    )

    // Greek Small Letter Stigma
    private val stigma = Pair(
        // Code
        byteArrayOf(0xCF.toByte(), 0x9B.toByte()),
        // Entity
        0x03DB.toInt()
    )

    // CJK Radical J-Simplified Even
    private val radical = Pair(
        // Code
        byteArrayOf(0xE2.toByte(), 0xBB.toByte(), 0xAB.toByte()),
        // Entity
        0x2EEB.toInt()
    )

    // Smiling Face with Heart-Shaped Eyes
    private val emoji = Pair(
        // Code
        byteArrayOf(0xF0.toByte(), 0x9F.toByte(), 0x98.toByte(), 0x8D.toByte()),
        // Entity
        0x1F60D.toInt()
    )

    private val r1 = byteArrayOf(0B1100_0000.toByte())
    private val r2 = byteArrayOf(0B1110_0000.toByte(), 0)
    private val r3 = byteArrayOf(0B1111_0000.toByte(), 0, 0)
    private val r4 = byteArrayOf(0B1111_1000.toByte(), 0, 0)
    private val r5 = byteArrayOf(0B1111_1100.toByte(), 0, 0, 0)

    private val w1 = byteArrayOf(0)
    private val w2 = byteArrayOf(0, 0)
    private val w3 = byteArrayOf(0, 0, 0)

    @Test
    fun testReadGlyph() {
        assertEquals(StreamStub(ascii.first).readGlyph().value, ascii.second)
        assertEquals(StreamStub(stigma.first).readGlyph().value, stigma.second)
        assertEquals(StreamStub(radical.first).readGlyph().value, radical.second)
        assertEquals(StreamStub(emoji.first).readGlyph().value, emoji.second)
    }

    @Test
    fun testWriteGlyph() {
        assertContentEquals(ascii.first, StreamStub(1).also { it.writeGlyph(ascii.second.toCodePoint()) }.buffer)
        assertContentEquals(stigma.first, StreamStub(2).also { it.writeGlyph(stigma.second.toCodePoint()) }.buffer)
        assertContentEquals(radical.first, StreamStub(3).also { it.writeGlyph(radical.second.toCodePoint()) }.buffer)
        assertContentEquals(emoji.first, StreamStub(4).also { it.writeGlyph(emoji.second.toCodePoint()) }.buffer)
    }

    @Test
    fun testReadGlyphAt() {
        assertEquals(BlockStub(ascii.first).readGlyphAt(0).value, ascii.second)
        assertEquals(BlockStub(stigma.first).readGlyphAt(0).value, stigma.second)
        assertEquals(BlockStub(radical.first).readGlyphAt(0).value, radical.second)
        assertEquals(BlockStub(emoji.first).readGlyphAt(0).value, emoji.second)

        assertFailsWith<IllegalArgumentException> { BlockStub(r1).readGlyphAt(0) }
        assertFailsWith<IllegalArgumentException> { BlockStub(r2).readGlyphAt(0) }
        assertFailsWith<IllegalArgumentException> { BlockStub(r3).readGlyphAt(0) }
        assertFailsWith<IllegalArgumentException> { BlockStub(r4).readGlyphAt(0) }
        assertFailsWith<IllegalArgumentException> { BlockStub(r5).readGlyphAt(0) }
    }

    @Test
    fun testWriteGlyphAt() {
        assertContentEquals(ascii.first, BlockStub(1).also { it.writeGlyphAt(0, ascii.second.toCodePoint()) }.buffer)
        assertContentEquals(stigma.first, BlockStub(2).also { it.writeGlyphAt(0, stigma.second.toCodePoint()) }.buffer)
        assertContentEquals(radical.first, BlockStub(3).also { it.writeGlyphAt(0, radical.second.toCodePoint()) }.buffer)
        assertContentEquals(emoji.first, BlockStub(4).also { it.writeGlyphAt(0, emoji.second.toCodePoint()) }.buffer)

        assertFailsWith<IllegalArgumentException> { BlockStub(w1).writeGlyphAt(0, 0x80.toCodePoint()) }
        assertFailsWith<IllegalArgumentException> { BlockStub(w2).writeGlyphAt(0, 0x800.toCodePoint()) }
        assertFailsWith<IllegalArgumentException> { BlockStub(w3).writeGlyphAt(0, 0x10000.toCodePoint()) }
    }

    private fun reconstructBlock(lipsum: String) {
        val text = BlockStub(lipsum.encodeToByteArray())
        val recon = BlockStub(text.buffer.size)
        var pos = 0
        do {
            val cp = text.readGlyphAt(pos)
            pos += recon.writeGlyphAt(pos, cp)
        } while(pos < recon.buffer.size)

        assertEquals(lipsum, recon.buffer.decodeToString())
    }

    private fun reconstructStream(lipsum: String) {
        val text = StreamStub(lipsum.encodeToByteArray())
        val recon = StreamStub(text.buffer.size)
        do {
            val cp = text.readGlyph()
            recon.writeGlyph(cp)
        } while(text.position < recon.buffer.size)

        assertEquals(lipsum, recon.buffer.decodeToString())
    }

    @Test
    fun testReconstructLatinBlock() {
        reconstructBlock(latinLipsum)
    }

    @Test
    fun testReconstructLatinStream() {
        reconstructStream(latinLipsum)
    }

    @Test
    fun testReconstructGreekBlock() {
        reconstructBlock(greekLipsum)
    }

    @Test
    fun testReconstructGreekStream() {
        reconstructStream(greekLipsum)
    }

    @Test
    fun testReconstructChineseBlock() {
        reconstructBlock(chineseLipsum)
    }

    @Test
    fun testReconstructChineseStream() {
        reconstructStream(chineseLipsum)
    }

    @Test
    fun testIsStartGlyph() = withUnicodeAware{
        assertTrue { isGlyphStart(ascii.first[0]) }
        assertTrue { isGlyphStart(stigma.first[0]) }
        assertTrue { isGlyphStart(radical.first[0]) }
        assertTrue { isGlyphStart(emoji.first[0]) }

        assertFalse { isGlyphStart(stigma.first[1]) }
        assertFalse { isGlyphStart(radical.first[1]) }
        assertFalse { isGlyphStart(emoji.first[1]) }
    }

    @Test
    fun testHasGlyphSize() = withUnicodeAware{
        assertEquals(hasGlyphSize(ascii.first[0]), 1)
        assertEquals(hasGlyphSize(stigma.first[0]), 2)
        assertEquals(hasGlyphSize(radical.first[0]), 3)
        assertEquals(hasGlyphSize(emoji.first[0]), 4)

        assertEquals(hasGlyphSize(stigma.first[1]), 0)
        assertEquals(hasGlyphSize(radical.first[1]), 0)
        assertEquals(hasGlyphSize(emoji.first[1]), 0)
    }
}