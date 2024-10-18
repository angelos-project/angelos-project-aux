package org.angproj.aux.pkg.prime

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType

import kotlin.test.Test
import kotlin.test.assertEquals

class ULongTypeTest {

    val first: ULong = 5259771533615348149u

    @Test
    fun enfoldToBlock() {
        val type = ULongType(first)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block, 0)

        val retrieved = ULongType.unfoldFromBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldToStream() {
        val type = ULongType(first)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = ULongType.unfoldFromStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}