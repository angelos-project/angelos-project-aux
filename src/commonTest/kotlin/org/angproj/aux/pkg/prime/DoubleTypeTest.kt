package org.angproj.aux.pkg.prime

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.binOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertEquals

class DoubleTypeTest {

    val first: Double = -1.9695734209401788E-261

    @Test
    fun enfoldToBlock() {
        val type = DoubleType(first)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK).toInt()))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block, 0)

        val retrieved = DoubleType.unfoldFromBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldToStream() {
        val type = DoubleType(first)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = DoubleType.unfoldFromStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}