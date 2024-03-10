package org.angproj.aux.pkg.type

import org.angproj.aux.TestInformationStub
import org.angproj.aux.util.BinHex
import org.angproj.aux.util.BufferSize
import org.angproj.aux.util.DataBuffer
import kotlin.test.Test
import kotlin.test.assertContentEquals

class BlockTypeTest {

    private val testData by lazy { BinHex.decodeToBin(TestInformationStub.data) }

    @Test
    fun enfoldToBlock() {
        val storage = BlockType(BufferSize._2K.size.toLong())

        (BufferSize._1K.size until (BufferSize._1K.size + 24)).forEach { size ->
            val block = BlockType(testData.sliceArray(0 until size))
            block.enfoldToBlock(storage, 0)

           val retrieved = BlockType.unfoldFromBlock(block, 0, size.toLong())
           assertContentEquals(block.block, retrieved.block)
        }
    }

    @Test
    fun enfoldToStream() {
        val stream = DataBuffer(BufferSize._2K.size)

        (BufferSize._1K.size until (BufferSize._1K.size + 24)).forEach { size ->
            stream.reset()
            val block = BlockType(testData.sliceArray(0 until size))
            block.enfoldToStream(stream)
            stream.flip()
            val retrieved = BlockType.unfoldFromStream(stream)
            assertContentEquals(block.block, retrieved.block)
        }
    }
}