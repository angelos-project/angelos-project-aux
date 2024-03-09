package org.angproj.aux.pkg.prime

import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.DataBuffer
import kotlin.test.Test
import kotlin.test.assertEquals

class IntTypeTest {

    val first: Int = -1508963019

    @Test
    fun enfoldToBlock() {
        val type = IntType(first)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block, 0)

        val retrieved = IntType.unfoldFromBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldToStream() {
        val type = IntType(first)
        val stream = DataBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = IntType.unfoldFromStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}