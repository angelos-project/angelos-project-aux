package org.angproj.aux.io

import org.angproj.aux.TestInformationStub
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.readGlyphAt
import kotlin.test.*


class TextTest {

    private val txtLen = TestInformationStub.lipsumMedium.length

    fun setInput(): Text = TestInformationStub.lipsumMedium.toText()

    @Test
    fun getSegment() { assertIs<Segment>(setInput().segment) }

    @Test
    fun getCapacity() { assertEquals(setInput().capacity, txtLen) }

    @Test
    fun getLimit() { assertEquals(setInput().limit, txtLen) }

    @Test
    fun limitAt() {
        val buf = setInput()

        assertFailsWith<IllegalArgumentException> { buf.limitAt(-1) }
        assertFailsWith<IllegalArgumentException> { buf.limitAt(buf.limit+1) }

        buf.limitAt(txtLen - 5)
        assertEquals(buf.limit, txtLen - 5)
    }

    @Test
    fun clear() {
        val buf = setInput()
        assertEquals(buf.limit, buf.capacity)

        buf.limitAt(txtLen - 5)
        assertEquals(buf.limit, txtLen - 5)

        buf.clear()
        assertEquals(buf.limit, txtLen)
    }

    private fun io(text: String) {
        val lipsum = text.encodeToByteArray()
        val buf = text.toText()

        var idx = 0
        while(idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            buf.storeGlyph(idx, cp)
            idx += cp.octetSize()
        }

        idx = 0
        val iter = buf.iterator()
        while(iter.hasNext()) {
            val cp = lipsum.readGlyphAt(idx)
            assertEquals(iter.next().value, cp.value)
            idx += cp.octetSize()
        }
    }

    @Test
    fun retrieveStoreGlyph() {
        io(TestInformationStub.latinLipsum)
        io(TestInformationStub.greekLipsum)
        io(TestInformationStub.chineseLipsum)
    }

    @Test
    fun asBinary() { assertIs<Segment>(setInput().asBinary().segment) }

    @Test
    fun isView() {
        assertEquals(setInput().isView(), false)
    }

    @Test
    fun isMem() {
        assertEquals(setInput().isMem(), false)
    }

    @Test
    fun close() {
        setInput().close()
    }

    @Test
    fun testNullText() {
        assertTrue(NullObject.text.isNull())
        assertFalse(Text().isNull())
    }
}