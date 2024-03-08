package org.angproj.aux.pkg.mem

import org.angproj.aux.pkg.DataBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class DoubleArrayTypeTest {

    val first = doubleArrayOf(-1.1274499625307711E14, 1.464455434580084E-24, -4.1152774742056765E-293)
    val second = doubleArrayOf(
        -9.180147825544662E-143, 8.882354166005901E145, 4.3510713287058534E-175,
        -9.878649542453361E25, 8.06845773968483E241
    )
    val third = doubleArrayOf(
        2.6844695967862244E178, 1.6578280087185313E80, -3.279012092802669E23,
        1.1494304409520904E-96, 1.3035478402495984E70, 2.4410472610912234E194,
        4.2626831867101333E-252
    )

    protected fun enfoldArrayToBlock(data: DoubleArray) {
        val type = DoubleArrayType(data)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = DoubleArrayType.unfoldFromBlock(block, type.value.size)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: DoubleArray) {
        val type = DoubleArrayType(data)
        val stream = DataBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = DoubleArrayType.unfoldFromStream(stream)
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