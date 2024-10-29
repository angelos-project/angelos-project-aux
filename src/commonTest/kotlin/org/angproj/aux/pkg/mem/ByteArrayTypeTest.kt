package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.ByteBuffer
import org.angproj.aux.buf.byteBuffer
import org.angproj.aux.buf.securelyRandomize
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.util.NullObject
import kotlin.test.Test
import kotlin.test.assertEquals


data class ByteTestObjectPackage(
    val fixBuffer: ByteBuffer = ByteBuffer(DataSize._32B), // Fixed size buffer
    var dynBuffer: ByteBuffer = NullObject.byteBuffer // Dynamic sized buffer
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(
        sizeOf(fixBuffer),
        sizeOf(dynBuffer)
    ).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveByteArray(fixBuffer)
        saveByteArray(dynBuffer)
    }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        loadByteArray(fixBuffer)
        dynBuffer = loadByteArray()
    }
}

data class ByteTestStructPackageable(
    val fixBuffer: ByteBuffer = ByteBuffer(DataSize._32B), // Fixed size buffer
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(
        sizeOf(fixBuffer),
    ).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) {
        saveByteArray(fixBuffer)
    }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) {
        loadByteArray(fixBuffer)
    }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class ByteTestObjectPackageable(
    val fixBuffer: ByteBuffer = ByteBuffer(DataSize._32B), // Fixed size buffer
    var dynBuffer: ByteBuffer = NullObject.byteBuffer // Dynamic sized buffer
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(
        sizeOf(fixBuffer),
        sizeOf(dynBuffer)
    ).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveByteArray(fixBuffer)
        saveByteArray(dynBuffer)
    }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        loadByteArray(fixBuffer)
        dynBuffer = loadByteArray()
    }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class ByteArrayTypeTest {

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = ByteTestObjectPackage()
        to1.fixBuffer.securelyRandomize()
        to1.dynBuffer = ByteBuffer(DataSize._128B).apply { securelyRandomize() }
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldToStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldFromStream(buf) { ByteTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = ByteTestStructPackageable()
        to1.fixBuffer.securelyRandomize()
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldToBlock(bin)
        bin.limitAt(len1)
        val to2 = StructType.unfoldFromBlock(bin) { ByteTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = ByteTestObjectPackageable()
        to1.fixBuffer.securelyRandomize()
        to1.dynBuffer = ByteBuffer(DataSize._128B).apply { securelyRandomize() }
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldToStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldFromStream(buf) { ByteTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }


    /*val first = byteArrayOf(
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
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK).toInt()))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldToBlock(block)

        val retrieved = ByteArrayType(ByteBuffer(type.value.limit))
        ByteArrayType.unfoldFromBlock(block, retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    protected fun enfoldArrayToStream(data: ByteBuffer) {
        val type = ByteArrayType(data)
        val stream = BinaryBuffer()
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
    }*/
}