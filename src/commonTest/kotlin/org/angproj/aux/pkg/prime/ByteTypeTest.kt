package org.angproj.aux.pkg.prime

import org.angproj.aux.TestInformationStub.refByte
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import kotlin.test.Test
import kotlin.test.assertEquals


data class ByteTestObjectPackage(
    var byte: Byte = 0
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(byte),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveByte(byte) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { byte = loadByte() }
}

data class ByteTestStructPackageable(
    var byte: Byte = 0
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(byte),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveByte(byte) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { byte = loadByte() }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class ByteTestObjectPackageable(
    var byte: Byte = 0
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(byte),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveByte(byte) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { byte = loadByte() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class ByteTypeTest {

    val testByte = refByte

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = ByteTestObjectPackage(testByte)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldToStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldFromStream(buf) { ByteTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = ByteTestStructPackageable(testByte)
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldToBlock(bin)
        bin.limitAt(len1)
        val to2 = StructType.unfoldFromBlock(bin) { ByteTestStructPackageable() }.value

        assertEquals(to1, to2)

        /**
         * Technically the Struct can be folded as an object on a stream, it seems unclear what to do
         *
         *         val to1 = ByteTestStructPackageable(testByte)
         *         val buf = BufMgr.binary(DataSize._4K.size)
         *         val len1 = StructType(to1).enfoldToStream(buf)
         *         buf.limitAt(len1)
         *         val to2 = StructType.unfoldFromStream(buf) { ByteTestStructPackageable() }.value
         *
         *         assertEquals(to1, to2)
         * */
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = ByteTestObjectPackageable(testByte)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldToStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldFromStream(buf) { ByteTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    /*val first: Byte = -31

    @Test
    fun enfoldToBlock() {
        val type = ByteType(first)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block, 0)

        val retrieved = ByteType.unfoldFromBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldToStream() {
        val type = ByteType(first)
        val stream = BinaryBuffer()
        type.enfoldToStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = ByteType.unfoldFromStream(stream)
        assertEquals(type.value, retrieved.value)
    }*/
}