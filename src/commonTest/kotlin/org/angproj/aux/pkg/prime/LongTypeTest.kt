import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.prime.LongType
import org.angproj.aux.pkg.type.BlockType

import kotlin.test.Test
import kotlin.test.assertEquals

class LongTypeTest {

    val first: Long = -523691854099259191

    @Test
    fun enfoldToBlock() {
        val type = LongType(first)
        val block = BlockType(type.foldSize(FoldFormat.BLOCK))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block, 0)

        val retrieved = LongType.unfoldFromBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldToStream() {
        val type = LongType(first)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

        val retrieved = LongType.unfoldFromStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}