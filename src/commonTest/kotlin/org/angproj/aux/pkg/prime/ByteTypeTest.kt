package org.angproj.aux.pkg.prime

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.binOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteTypeTest {

    val first: Byte = -31

    @Test
    fun enfoldToBlock() {
        val type = ByteType(first)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK).toInt()))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block, 0)

        val retrieved = ByteType.unfoldFromBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldToStream() {
        val type = ByteType(first)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = ByteType.unfoldFromStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}