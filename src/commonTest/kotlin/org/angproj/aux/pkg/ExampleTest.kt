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
): Package {

    override fun foldSize(foldFormat: FoldFormat): Long = withFoldSize(foldFormat) { listOf(
        sizeOf(uuid),
        sizeOf(text),
    ).sum() }

    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveUuid4(uuid)
        saveString(text)
    }

    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        uuid = loadUuid4()
        text = loadString()
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TestObject) return false

        if (uuid != other.uuid) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + text.hashCode()
        return result
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

    override fun foldSize(foldFormat: FoldFormat): Long = withFoldSize(foldFormat){ listOf(
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

    override fun enfold(outData: Storable, offset: Int): Long = withEnfold(outData) {
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

    override fun unfold(inData: Retrievable, offset: Int): Long = withUnfold(inData, offset) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TestStruct) return false

        if (uuid != other.uuid) return false
        if (byte != other.byte) return false
        if (short != other.short) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (float != other.float) return false
        if (double != other.double) return false
        if (uByte != other.uByte) return false
        if (uShort != other.uShort) return false
        if (uInt != other.uInt) return false
        if (uLong != other.uLong) return false
        if (byteArray != other.byteArray) return false
        if (shortArray != other.shortArray) return false
        if (intArray != other.intArray) return false
        if (longArray != other.longArray) return false
        if (floatArray != other.floatArray) return false
        if (doubleArray != other.doubleArray) return false
        if (uByteArray != other.uByteArray) return false
        if (uShortArray != other.uShortArray) return false
        if (uIntArray != other.uIntArray) return false
        if (uLongArray != other.uLongArray) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + byte
        result = 31 * result + short
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + float.hashCode()
        result = 31 * result + double.hashCode()
        result = 31 * result + uByte.hashCode()
        result = 31 * result + uShort.hashCode()
        result = 31 * result + uInt.hashCode()
        result = 31 * result + uLong.hashCode()
        result = 31 * result + byteArray.hashCode()
        result = 31 * result + shortArray.hashCode()
        result = 31 * result + intArray.hashCode()
        result = 31 * result + longArray.hashCode()
        result = 31 * result + floatArray.hashCode()
        result = 31 * result + doubleArray.hashCode()
        result = 31 * result + uByteArray.hashCode()
        result = 31 * result + uShortArray.hashCode()
        result = 31 * result + uIntArray.hashCode()
        result = 31 * result + uLongArray.hashCode()
        return result
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
            byteArray.securelyRandomize()
            shortArray.securelyRandomize()
            intArray.securelyRandomize()
            longArray.securelyRandomize()
            floatArray.securelyRandomize()
            doubleArray.securelyRandomize()
            uByteArray.securelyRandomize()
            uShortArray.securelyRandomize()
            uIntArray.securelyRandomize()
            uLongArray.securelyRandomize()
        }
    }
}


class ExampleTest {

    @Test
    fun tryObject() {
        val to1 = TestObject(uuid4())
        val buf = BinaryBuffer()
        ObjectType(to1).enfoldToStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldFromStream(buf) { TestObject() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun tryStruct() {
        val to1 = TestStruct.randomize()
        val buf = BinaryBuffer()
        StructType(to1).enfoldToStream(buf)
        buf.flip()

        val to2 = StructType.unfoldFromStream(buf) { TestStruct() }.value

        assertEquals(to1, to2)
    }
}