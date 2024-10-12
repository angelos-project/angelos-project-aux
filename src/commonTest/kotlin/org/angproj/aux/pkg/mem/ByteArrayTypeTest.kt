package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.ByteBuffer
import org.angproj.aux.buf.toByteBuffer
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.*


class ByteArrayTypeTest {

    val first = byteArrayOf(
        (-31123).mod(128), (-25444).mod(128), 9662.mod(128)
    ).toByteBuffer()
    val second = byteArrayOf(
        12959.mod(128), 28616.mod(128), 2619.mod(128),
        17090.mod(128), (-31423).mod(128)
    ).toByteBuffer()
    val third = byteArrayOf(
        20417.mod(128), 11571.mod(128), 30775.mod(128),
        2691.mod(128), 26005.mod(128), (-28863).mod(128),
        (-1299).mod(128)
    ).toByteBuffer()

    protected fun enfoldArrayToBlock(data: ByteBuffer) {
        val type = ByteArrayType(data)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = ByteArrayType.unfoldFromBlock(block, type.value.limit)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: ByteBuffer) {
        val type = ByteArrayType(data)
        val stream = DataBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = ByteArrayType.unfoldFromStream(stream)
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