package org.angproj.aux.pkg

import org.angproj.aux.buf.*
import org.angproj.aux.io.*
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


data class TestObject(
    var uuid: Uuid4 = NullObject.uuid4,
    var text: Text = "Hello, world!".toText(),
    val byteArray: ByteBuffer = ByteBuffer(DataSize._32B), // Fixed size array
    var byteArray2: ByteBuffer = NullObject.byteBuffer, // Variable size uninitialized array (OBSERVE! var)
    val shortArray: ShortBuffer = ShortBuffer(DataSize._32B),
    var shortArray2: ShortBuffer = NullObject.shortBuffer,
    val intArray: IntBuffer = IntBuffer(DataSize._32B),
    var intArray2: IntBuffer = NullObject.intBuffer,
    val longArray: LongBuffer = LongBuffer(DataSize._32B),
    var longArray2: LongBuffer = NullObject.longBuffer,
    val floatArray: FloatBuffer = FloatBuffer(DataSize._32B),
    var floatArray2: FloatBuffer = NullObject.floatBuffer,
    val doubleArray: DoubleBuffer = DoubleBuffer(DataSize._32B),
    var doubleArray2: DoubleBuffer = NullObject.doubleBuffer,
    val uByteArray: UByteBuffer = UByteBuffer(DataSize._32B),
    var uByteArray2: UByteBuffer = NullObject.uByteBuffer,
    val uShortArray: UShortBuffer = UShortBuffer(DataSize._32B),
    var uShortArray2: UShortBuffer = NullObject.uShortBuffer,
    val uIntArray: UIntBuffer = UIntBuffer(DataSize._32B),
    var uIntArray2: UIntBuffer = NullObject.uIntBuffer,
    val uLongArray: ULongBuffer = ULongBuffer(DataSize._32B),
    var uLongArray2: ULongBuffer = NullObject.uLongBuffer
): Package {

    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(
        sizeOf(uuid),
        sizeOf(text),
        sizeOf(byteArray),
        sizeOf(byteArray2),
        sizeOf(shortArray),
        sizeOf(shortArray2),
        sizeOf(intArray),
        sizeOf(intArray2),
        sizeOf(longArray),
        sizeOf(longArray2),
        sizeOf(floatArray),
        sizeOf(floatArray2),
        sizeOf(doubleArray),
        sizeOf(doubleArray2),
        sizeOf(uByteArray),
        sizeOf(uByteArray2),
        sizeOf(uShortArray),
        sizeOf(uShortArray2),
        sizeOf(uIntArray),
        sizeOf(uIntArray2),
        sizeOf(uLongArray),
        sizeOf(uLongArray2)
    ).sum() }

    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveUuid4(uuid)
        saveString(text)
        saveByteArray(byteArray)
        saveByteArray(byteArray2)
        saveShortArray(shortArray)
        saveShortArray(shortArray2)
        saveIntArray(intArray)
        saveIntArray(intArray2)
        saveLongArray(longArray)
        saveLongArray(longArray2)
        saveFloatArray(floatArray)
        saveFloatArray(floatArray2)
        saveDoubleArray(doubleArray)
        saveDoubleArray(doubleArray2)
        saveUByteArray(uByteArray)
        saveUByteArray(uByteArray2)
        saveUShortArray(uShortArray)
        saveUShortArray(uShortArray2)
        saveUIntArray(uIntArray)
        saveUIntArray(uIntArray2)
        saveULongArray(uLongArray)
        saveULongArray(uLongArray2)
    }

    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        uuid = loadUuid4()
        text = loadString()
        loadByteArray(byteArray)
        byteArray2 = loadByteArray()
        loadShortArray(shortArray)
        shortArray2 = loadShortArray()
        loadIntArray(intArray)
        intArray2 = loadIntArray()
        loadLongArray(longArray)
        longArray2 = loadLongArray()
        loadFloatArray(floatArray)
        floatArray2 = loadFloatArray()
        loadDoubleArray(doubleArray)
        doubleArray2 = loadDoubleArray()
        loadUByteArray(uByteArray)
        uByteArray2 = loadUByteArray()
        loadUShortArray(uShortArray)
        uShortArray2 = loadUShortArray()
        loadUIntArray(uIntArray)
        uIntArray2 = loadUIntArray()
        loadULongArray(uLongArray)
        uLongArray2 = loadULongArray()
    }
}


data class TestStruct(
    val uuid: Uuid4 = uuid4(),
    var byte: Byte = 0,
    var short: Short = 0,
    var int: Int = 0,
    var long: Long = 0,
    var float: Float = .0f,
    var double: Double = .0,
    var uByte: UByte = 0u,
    var uShort: UShort = 0u,
    var uInt: UInt = 0u,
    var uLong: ULong = 0u,
    val byteArray: ByteBuffer = ByteBuffer(DataSize._32B),
    val shortArray: ShortBuffer = ShortBuffer(DataSize._32B),
    val intArray: IntBuffer = IntBuffer(DataSize._32B),
    val longArray: LongBuffer = LongBuffer(DataSize._32B),
    val floatArray: FloatBuffer = FloatBuffer(DataSize._32B),
    val doubleArray: DoubleBuffer = DoubleBuffer(DataSize._32B),
    val uByteArray: UByteBuffer = UByteBuffer(DataSize._32B),
    val uShortArray: UShortBuffer = UShortBuffer(DataSize._32B),
    val uIntArray: UIntBuffer = UIntBuffer(DataSize._32B),
    val uLongArray: ULongBuffer = ULongBuffer(DataSize._32B)
): Packageable {

    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK

    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(
        sizeOf(uuid),
        sizeOf(byte),
        sizeOf(short),
        sizeOf(int),
        sizeOf(long),
        sizeOf(float),
        sizeOf(double),
        sizeOf(uByte),
        sizeOf(uShort),
        sizeOf(uInt),
        sizeOf(uLong),
        sizeOf(byteArray),
        sizeOf(shortArray),
        sizeOf(intArray),
        sizeOf(longArray),
        sizeOf(floatArray),
        sizeOf(doubleArray),
        sizeOf(uByteArray),
        sizeOf(uShortArray),
        sizeOf(uIntArray),
        sizeOf(uLongArray)
    ).sum() }

    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) {
        saveUuid4(uuid)
        saveByte(byte)
        saveShort(short)
        saveInt(int)
        saveLong(long)
        saveFloat(float)
        saveDouble(double)
        saveUByte(uByte)
        saveUShort(uShort)
        saveUInt(uInt)
        saveULong(uLong)
        saveByteArray(byteArray)
        saveShortArray(shortArray)
        saveIntArray(intArray)
        saveLongArray(longArray)
        saveFloatArray(floatArray)
        saveDoubleArray(doubleArray)
        saveUByteArray(uByteArray)
        saveUShortArray(uShortArray)
        saveUIntArray(uIntArray)
        saveULongArray(uLongArray)
    }

    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) {
        loadUuid4(uuid)
        byte = loadByte()
        short = loadShort()
        int = loadInt()
        long = loadLong()
        float = loadFloat()
        double = loadDouble()
        uByte = loadUByte()
        uShort = loadUShort()
        uInt = loadUInt()
        uLong = loadULong()
        loadByteArray(byteArray)
        loadShortArray(shortArray)
        loadIntArray(intArray)
        loadLongArray(longArray)
        loadFloatArray(floatArray)
        loadDoubleArray(doubleArray)
        loadUByteArray(uByteArray)
        loadUShortArray(uShortArray)
        loadUIntArray(uIntArray)
        loadULongArray(uLongArray)
    }

    companion object {
        fun randomize(): TestStruct = TestStruct().apply {
            byte = SecureRandom.readByte()
            short = SecureRandom.readShort()
            int = SecureRandom.readInt()
            long = SecureRandom.readLong()
            float = SecureRandom.readFloat()
            double = SecureRandom.readDouble()
            uByte = SecureRandom.readUByte()
            uShort = SecureRandom.readUShort()
            uInt = SecureRandom.readUInt()
            uLong = SecureRandom.readULong()
            byteArray.random()
            shortArray.random()
            intArray.random()
            longArray.random()
            floatArray.random()
            doubleArray.random()
            uByteArray.random()
            uShortArray.random()
            uIntArray.random()
            uLongArray.random()
        }
    }
}


class ExampleTest {

    @Test
    fun tryObject() {
        val to1 = TestObject(uuid4())
        val buf = BinaryBuffer()
        ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { TestObject() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun tryStruct() {
        val to1 = TestStruct.randomize()
        val buf = BinaryBuffer()
        StructType(to1).enfoldStream(buf)
        buf.flip()

        val to2 = StructType.unfoldStream(buf) { TestStruct() }.value

        assertEquals(to1, to2)
    }
}