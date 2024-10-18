package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.UByteBuffer
import org.angproj.aux.buf.toByteBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class UByteArrayTypeTest {

    val first = UByteBuffer(byteArrayOf(
        (-31123).mod(128), (-25444).mod(128), 9662.mod(128)
    ).toByteBuffer().segment)
    val second = UByteBuffer(byteArrayOf(
        12959.mod(128), 28616.mod(128), 2619.mod(128),
        17090.mod(128), (-31423).mod(128)
    ).toByteBuffer().segment)
    val third = UByteBuffer(byteArrayOf(
        20417.mod(128), 11571.mod(128), 30775.mod(128),
        2691.mod(128), 26005.mod(128), (-28863).mod(128),
        (-1299).mod(128)
    ).toByteBuffer().segment)

    protected fun enfoldArrayToBlock(data: UByteBuffer) {
        val type = UByteArrayType(data)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = UByteArrayType(UByteBuffer(type.value.limit))
        UByteArrayType.unfoldFromBlock(block, retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: UByteBuffer) {
        val type = UByteArrayType(data)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = UByteArrayType.unfoldFromStream(stream)
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