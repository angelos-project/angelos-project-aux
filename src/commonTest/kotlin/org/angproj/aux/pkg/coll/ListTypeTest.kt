package org.angproj.aux.pkg.coll

import org.angproj.aux.TestInformationStub.refByte
import org.angproj.aux.TestInformationStub.refDouble
import org.angproj.aux.TestInformationStub.refFloat
import org.angproj.aux.TestInformationStub.refInt
import org.angproj.aux.TestInformationStub.refLong
import org.angproj.aux.TestInformationStub.refShort
import org.angproj.aux.TestInformationStub.refUByte
import org.angproj.aux.TestInformationStub.refUInt
import org.angproj.aux.TestInformationStub.refULong
import org.angproj.aux.TestInformationStub.refUShort
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.TestObject
import org.angproj.aux.pkg.TestStruct
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


class ListTypeTest {

    private fun setInputTestObject(): TestObject = TestObject(
        uuid4()
    )

    private fun setInputTestStruct(): TestStruct = TestStruct(
        uuid4(),
        refByte,
        refShort,
        refInt,
        refLong,
        refFloat,
        refDouble,
        refUByte,
        refUShort,
        refUInt,
        refULong,
    )

    @Test
    fun enfoldUnfoldObject() {
        val pl1 = List(10) { setInputTestObject() }
        val buf = BinaryBuffer()
        val lt = ListType(pl1)
        lt.enfoldToStream(buf)
        buf.flip()

        assertEquals(lt.foldSize(FoldFormat.STREAM).toInt(), buf.limit)

        val pl2 = ListType.unfoldFromStream(buf) { TestObject() }.value

        assertEquals(pl1, pl2)
    }

    @Test
    fun enfoldUnfoldStruct() {
        val pl1 = List(10) { setInputTestStruct() }
        val buf = BinaryBuffer()
        val lt = ListType(pl1)
        lt.enfoldToStream(buf)
        buf.flip()

        assertEquals(lt.foldSize(FoldFormat.BLOCK).toInt(), buf.limit)

        val pl2 = ListType.unfoldFromStream(buf) { TestStruct() }.value

        assertEquals(pl1, pl2)
    }
}