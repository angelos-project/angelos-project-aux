package org.angproj.aux.pkg.mem

import org.angproj.aux.util.DataBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ShortArrayTypeTest {

    val first = shortArrayOf(-31123, -25444, 9662)
    val second = shortArrayOf(12959, 28616, 2619, 17090, -31423)
    val third = shortArrayOf(20417, 11571, 30775, 2691, 26005, -28863, -1299)

    protected fun enfoldArrayToBlock(data: ShortArray) {
        val type = ShortArrayType(data)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = ShortArrayType.unfoldFromBlock(block, type.value.size)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: ShortArray) {
        val type = ShortArrayType(data)
        val stream = DataBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = ShortArrayType.unfoldFromStream(stream)
        assertContentEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldToBlock() {
        enfoldArrayToBlock(first)
        enfoldArrayToBlock(second)
        enfoldArrayToBlock(third)
    }

    @Test
    fun enfoldToStream() {
        enfoldArrayToStream(first)
        enfoldArrayToStream(second)
        enfoldArrayToStream(third)
    }
}