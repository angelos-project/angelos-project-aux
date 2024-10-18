package org.angproj.aux.pkg.arb

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.toByteArray
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


class Uuid4TypeTest {

    @Test
    fun enfoldToBlock() {
        val type = Uuid4Type(uuid4())
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))

        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = Uuid4Type.unfoldFromBlock(block)
        assertEquals(type, retrieved)
    }

    @Test
    fun enfoldToStream() {
        val type = Uuid4Type(uuid4())
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()

        assertEquals(type.foldSize(FoldFormat.STREAM).toInt(), stream.limit)

        val retrieved = Uuid4Type.unfoldFromStream(stream)
        assertEquals(type, retrieved)
    }
}