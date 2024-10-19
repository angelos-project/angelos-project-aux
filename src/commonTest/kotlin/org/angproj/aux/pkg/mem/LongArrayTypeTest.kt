package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.LongBuffer
import org.angproj.aux.buf.toLongBuffer
import org.angproj.aux.io.binOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class LongArrayTypeTest {

    val first = longArrayOf(
        7350606340558794086, 4605821548019215976, -3128307633535295103
    ).toLongBuffer()
    val second = longArrayOf(
        7945849346388654169, -4297059938720181180, -1681121171521567076,
        8719833755577087749, -7527003565038888806
    ).toLongBuffer()
    val third = longArrayOf(
        -6674685465426633689, -4828168200396455132, 1963319562884594548,
        5797122671369353319, -6876203796469653941, -2417569782213643756,
        5235272198954834514
    ).toLongBuffer()

    protected fun enfoldArrayToBlock(data: LongBuffer) {
        val type = LongArrayType(data)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK).toInt()))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = LongArrayType(LongBuffer(type.value.limit))
        LongArrayType.unfoldFromBlock(block, retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: LongBuffer) {
        val type = LongArrayType(data)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = LongArrayType.unfoldFromStream(stream)
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