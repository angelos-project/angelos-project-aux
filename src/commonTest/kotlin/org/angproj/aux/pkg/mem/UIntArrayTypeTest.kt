package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.UIntBuffer
import org.angproj.aux.buf.toIntBuffer
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class UIntArrayTypeTest {

    val first = UIntBuffer(intArrayOf(
        -867525528, -424427028, -200053096
    ).toIntBuffer().segment)
    val second = UIntBuffer(intArrayOf(
        393483776, -1786415628, -947006215, 1631859464, 1469943351
    ).toIntBuffer().segment)
    val third = UIntBuffer(intArrayOf(
        -1041512378, 2027971732, -937853390, 147748328, 2042021213, 1496947466, -452068048
    ).toIntBuffer().segment)

    protected fun enfoldArrayToBlock(data: UIntBuffer) {
        val type = UIntArrayType(data)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = UIntArrayType.unfoldFromBlock(block, type.value.limit)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: UIntBuffer) {
        val type = UIntArrayType(data)
        val stream = DataBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = UIntArrayType.unfoldFromStream(stream)
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