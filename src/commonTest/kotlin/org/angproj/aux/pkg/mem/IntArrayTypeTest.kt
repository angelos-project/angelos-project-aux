package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.IntBuffer
import org.angproj.aux.buf.toIntBuffer
import org.angproj.aux.io.binOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class IntArrayTypeTest {

    val first = intArrayOf(-867525528, -424427028, -200053096).toIntBuffer()
    val second = intArrayOf(393483776, -1786415628, -947006215, 1631859464, 1469943351).toIntBuffer()
    val third = intArrayOf(
        -1041512378, 2027971732, -937853390, 147748328, 2042021213, 1496947466, -452068048).toIntBuffer()

    protected fun enfoldArrayToBlock(data: IntBuffer) {
        val type = IntArrayType(data)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK).toInt()))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = IntArrayType(IntBuffer(type.value.limit))
        IntArrayType.unfoldFromBlock(block,retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: IntBuffer) {
        val type = IntArrayType(data)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = IntArrayType.unfoldFromStream(stream)
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