package org.angproj.aux.io

import org.angproj.aux.TestInformationStub
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.readGlyphAt
import kotlin.test.*


class TextTest: MemBlockTest<Text>() {

    override val txtLen = TestInformationStub.lipsumMedium.length

    override fun setInput(): Text = TestInformationStub.lipsumMedium.toText()

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
    fun testNullText() {
        assertTrue(NullObject.text.isNull())
        assertFalse(Text().isNull())
    }
}