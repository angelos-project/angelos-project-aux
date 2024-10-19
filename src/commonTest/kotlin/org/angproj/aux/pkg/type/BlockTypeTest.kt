package org.angproj.aux.pkg.type

import org.angproj.aux.TestInformationStub
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.binOf
import org.angproj.aux.io.toBinary
import org.angproj.aux.io.toByteArray
import org.angproj.aux.util.BinHex
import kotlin.test.Test
import kotlin.test.assertContentEquals

class BlockTypeTest {

    private val testData by lazy { BinHex.decodeToBin(TestInformationStub.data) }

    @Test
    fun enfoldToBlock() {
        val storage = BlockType(binOf(DataSize._2K.size))

        (DataSize._1K.size until (DataSize._1K.size + 24)).forEach { size ->
            val block = BlockType(testData.sliceArray(0 until size).toBinary())
            block.enfoldToBlock(storage, 0)

           val retrieved = BlockType.unfoldFromBlock(block, 0, size.toLong())
           assertContentEquals(block.block.toByteArray(), retrieved.block.toByteArray())
        }
    }

    @Test
    fun enfoldToStream() {
        val stream = BinaryBuffer(DataSize._2K.size)

        (DataSize._1K.size until (DataSize._1K.size + 24)).forEach { size ->
            stream.clear()
            val block = BlockType(testData.sliceArray(0 until  size).toBinary())
            block.enfoldToStream(stream)
            stream.flip()
            val retrieved = BlockType.unfoldFromStream(stream)
            assertContentEquals(block.block.toByteArray(), retrieved.block.toByteArray())
        }
    }
}