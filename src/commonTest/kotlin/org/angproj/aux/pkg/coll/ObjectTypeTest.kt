package org.angproj.aux.pkg.coll

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.TestObject
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


class ObjectTypeTest {

    private fun setInputTestObject(): TestObject = TestObject(
        uuid4()
    )

    @Test
    fun enfoldUnfoldStream() {
        val pl1 = setInputTestObject()
        val buf = BinaryBuffer()
        val lt = ObjectType(pl1)
        lt.enfoldToStream(buf)
        buf.flip()

        assertEquals(lt.foldSize(FoldFormat.STREAM).toInt(), buf.limit)

        val pl2 = ObjectType.unfoldFromStream(buf) { TestObject() }.value

        assertEquals(pl1, pl2)
    }
}