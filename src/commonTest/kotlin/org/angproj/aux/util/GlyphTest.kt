package org.angproj.aux.util

import org.angproj.aux.sec.SecureRandom
import kotlin.test.Test

class GlyphTest {

    @Test
    fun isValid() {
    }

    @Test
    fun getSize() {
    }

    @Test
    fun toUtfString() {
    }

    @Test
    fun glyphSize() {
    }

    @Test
    fun isGlyphValid() {
    }

    @Test
    fun glyphRead() {
    }

    @Test
    fun glyphWrite() {
    }

    @Test
    fun readGlyphAt() {
    }

    @Test
    fun testReadGlyphAt() {
    }

    @Test
    fun writeGlyphAt() {
    }

    @Test
    fun testWriteGlyphAt() {
    }

    @Test
    fun testTrickAndTreat() {
        val buffer = DataBuffer()
        buffer.limit = SecureRandom.readLine(buffer.asByteArray())
        println(buffer.asByteArray().decodeToString())
        /*var glyph = 0
        do {
            glyph = SecureRandom.readGlyph()
            println("U+${glyph.toString(16)} - ${glyph.isValid()}")
        } while(glyph.isValid())*/
    }
}