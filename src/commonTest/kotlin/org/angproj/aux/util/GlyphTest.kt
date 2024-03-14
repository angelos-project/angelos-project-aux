package org.angproj.aux.util

import org.angproj.aux.rand.InitializationVector
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.utf.*
import org.angproj.aux.utf.GLYPH_HOLE
import kotlin.test.Test
import kotlin.test.assertEquals

class Glyph2279Iterator : Iterator<Glyph> {
    private val iv: Int = InitializationVector.IV_CA53.iv.toInt() and MASK

    private var _counter: Int = 0
    val counter: Int
        get() = _counter

    private var seed: Int = iv

    override fun hasNext(): Boolean = _counter < MASK

    override fun next(): Glyph {
        seed = (-seed.inv() * 13) and MASK
        _counter++
        return seed
    }

    companion object{
        private const val MASK: Int = 0x1FFFFF
    }
}

class Glyph3629Iterator : Iterator<Glyph> {
    private var counter: Int = 0

    override fun hasNext(): Boolean = counter < G_MAX

    override fun next(): Glyph = counter + if(counter++ in GLYPH_HOLE) G_HOLE else 0

    companion object {
        private val G_HOLE = GLYPH_HOLE.last - GLYPH_HOLE.first
        private val G_MAX: Int = GLYPH_MAX_VALUE - G_HOLE
    }
}

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
        assertEquals(glyphSize(0B0111_1111.toByte()), 1)
        assertEquals(glyphSize(0B1101_1111.toByte()), 2)
        assertEquals(glyphSize(0B1110_1111.toByte()), 3)
        assertEquals(glyphSize(0B1111_0111.toByte()), 4)
        assertEquals(glyphSize(0B1111_1011.toByte()), 5)
        assertEquals(glyphSize(0B1111_1101.toByte()), 6)
        assertEquals(glyphSize(0B1111_1111.toByte()), -1)
        assertEquals(glyphSize(0B1000_0000.toByte()), -1)
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
        println(UtfString(buffer.asByteArray()).toString())
    }

    @Test
    fun testTrickAndTreat2() {
        val buffer = "Å".encodeToByteArray()
        println(glyphSize(buffer[0]))
        println(buffer.readGlyphAt(0))
    }

    @Test
    fun testReadWriteByteArray() {
        val utf = ByteArray(4)
        val generator = Glyph3629Iterator()
        generator.forEach { glyph ->
            utf.writeGlyphAt(0, glyph)
            val codePoint = utf.readGlyphAt(0)
            if(codePoint != glyph) {
                println("U+${glyph.toString(16)} - U+${codePoint.toString(16)}: " + utf.decodeToString())
            }
            assertEquals(glyph, codePoint)
        }
    }
}