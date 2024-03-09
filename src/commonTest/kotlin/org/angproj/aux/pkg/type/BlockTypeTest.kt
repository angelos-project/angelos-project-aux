package org.angproj.aux.pkg.type

import org.angproj.aux.TestInformationStub
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.prime.ByteType
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.sec.SecureFeed
import org.angproj.aux.util.BinHex
import org.angproj.aux.util.BufferSize
import org.angproj.aux.util.DataBuffer
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class BlockTypeTest {

    val testData by lazy { BinHex.decodeToBin(TestInformationStub.data) }

    @Test
    fun enfoldToBlock() {
        val storage = BlockType(BufferSize._2K.size.toLong())

        (BufferSize._1K.size until (BufferSize._1K.size + 24)).forEach { size ->
            println(size)
            val block = BlockType(testData.sliceArray(0 until size))
            block.enfoldToBlock(storage, 0)

            val retrieved = BlockType.unfoldFromBlock(block, 0, size.toLong())
            assertContentEquals(block.block, retrieved.block)
        }
    }

    @Test
    fun enfoldToStream() {
        /*val type = ByteType(first)
        val stream = DataBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = ByteType.unfoldFromStream(stream)
        assertEquals(type.value, retrieved.value) */
    }
}