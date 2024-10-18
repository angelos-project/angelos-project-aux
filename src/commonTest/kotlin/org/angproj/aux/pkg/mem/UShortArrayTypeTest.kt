package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.UShortBuffer
import org.angproj.aux.buf.toShortBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class UShortArrayTypeTest {

    val first = UShortBuffer(shortArrayOf(-31123, -25444, 9662).toShortBuffer().segment)
    val second = UShortBuffer(shortArrayOf(12959, 28616, 2619, 17090, -31423).toShortBuffer().segment)
    val third = UShortBuffer(shortArrayOf(20417, 11571, 30775, 2691, 26005, -28863, -1299).toShortBuffer().segment)

    protected fun enfoldArrayToBlock(data: UShortBuffer) {
        val type = UShortArrayType(data)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = UShortArrayType(UShortBuffer(type.value.limit))
        UShortArrayType.unfoldFromBlock(block, retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: UShortBuffer) {
        val type = UShortArrayType(data)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = UShortArrayType.unfoldFromStream(stream)
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