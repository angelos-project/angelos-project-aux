package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.FloatBuffer
import org.angproj.aux.buf.toFloatBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class FloatArrayTypeTest {

    val first = floatArrayOf(0.061455287F, -6.5226563E-6F, 2.3474232E18F).toFloatBuffer()
    val second = floatArrayOf(
        3.7015142E25F, 6.974464E16F, -1.1948615E-35F,
        2.099392E22F, -7.8376166E-27F).toFloatBuffer()
    val third = floatArrayOf(
        -7.992098E-39F, 3.8771734E28F, -7.3174546E-9F,
        -1.8449082E-22F, 1.2389568E-23F, -58464.027F,
        -2.8491732E-14F
    ).toFloatBuffer()

    protected fun enfoldArrayToBlock(data: FloatBuffer) {
        val type = FloatArrayType(data)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = FloatArrayType(FloatBuffer(type.value.limit))
        FloatArrayType.unfoldFromBlock(block, retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: FloatBuffer) {
        val type = FloatArrayType(data)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = FloatArrayType.unfoldFromStream(stream)
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