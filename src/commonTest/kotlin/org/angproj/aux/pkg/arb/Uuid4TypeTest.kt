package org.angproj.aux.pkg.arb

import org.angproj.aux.io.toByteArray
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Uuid4TypeTest {

    @Test
    fun enfoldToBlock() {
        val type = Uuid4Type(uuid4())
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = Uuid4Type.unfoldFromBlock(block)
        assertContentEquals(type.value.toBinary().toByteArray(), retrieved.value.toBinary().toByteArray())
    }

    @Test
    fun enfoldToStream() {
        val type = Uuid4Type(uuid4())
        val stream = DataBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = Uuid4Type.unfoldFromStream(stream)
        assertContentEquals(type.value.toBinary().toByteArray(), retrieved.value.toBinary().toByteArray())
    }
}