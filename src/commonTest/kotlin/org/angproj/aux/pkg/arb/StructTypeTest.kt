package org.angproj.aux.pkg.arb

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.binOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.TestStruct
import kotlin.test.Test
import kotlin.test.assertEquals


class StructTypeTest {

    private fun setInputTestStruct(): TestStruct = TestStruct.randomize()

    @Test
    fun enfoldUnfoldBlock() {
        val pl1 = setInputTestStruct()
        val lt = StructType(pl1)
        val buf = binOf(lt.foldSize(FoldFormat.BLOCK).toInt())
        val length = lt.enfoldToBlock(buf)

        assertEquals(length.toInt(), buf.limit)
        assertEquals(lt.foldSize(FoldFormat.BLOCK).toInt(), buf.limit)

        val pl2 = StructType.unfoldFromBlock(buf) { TestStruct() }.value

        assertEquals(pl1, pl2)
    }

    @Test
    fun enfoldUnfoldStream() {
        val pl1 = setInputTestStruct()
        val buf = BinaryBuffer()
        val lt = StructType(pl1)
        val length = lt.enfoldToStream(buf)
        buf.flip()

        assertEquals(length.toInt(), buf.limit)
        assertEquals(lt.foldSize(FoldFormat.STREAM).toInt(), buf.limit)

        val pl2 = StructType.unfoldFromStream(buf) { TestStruct() }.value

        assertEquals(pl1, pl2)
    }
}