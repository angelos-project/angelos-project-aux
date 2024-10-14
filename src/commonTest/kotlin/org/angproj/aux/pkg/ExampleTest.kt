package org.angproj.aux.pkg

import org.angproj.aux.io.*
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


data class TestObject(
    var uuid: Uuid4 = NullObject.uuid4,
    var text: Text = "Hello, world!".toText()
): Packageable {

    override val foldFormat: FoldFormat = FoldFormat.STREAM

    override fun foldSize(foldFormat: FoldFormat): Long = withFoldSize(foldFormat) { listOf(
        sizeOf(uuid),
        sizeOf(text),
    ).sum() }

    override fun enfold(outStream: Writable): Unit = withEnfold(outStream) {
        saveUuid4(uuid)
        saveString(text)
    }

    override fun unfold(inStream: Readable): Unit = withUnfold(inStream) {
        uuid = loadUuid4()
        text = loadString()
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + foldFormat.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TestObject) return false

        if (uuid != other.uuid) return false
        if (text != other.text) return false
        if (foldFormat != other.foldFormat) return false

        return true
    }
}


data class TestStruct(
    var uuid: Uuid4 = NullObject.uuid4,
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
): Packageable {

    override val foldFormat: FoldFormat = FoldFormat.BLOCK

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
        sizeOf(uLong)
    ).sum() }

    override fun enfold(outData: Storable, offset: Int): Unit = withEnfold(outData) {
        var pos = saveUuid4(offset, uuid)
        pos += saveByte(pos.toInt(), byte)
        pos += saveShort(pos.toInt(), short)
        pos += saveInt(pos.toInt(), int)
        pos += saveLong(pos.toInt(), long)
        pos += saveFloat(pos.toInt(), float)
        pos += saveDouble(pos.toInt(), double)
        pos += saveUByte(pos.toInt(), uByte)
        pos += saveUShort(pos.toInt(), uShort)
        pos += saveUInt(pos.toInt(), uInt)
        saveULong(pos.toInt(), uLong)
    }

    override fun unfold(inData: Retrievable): Unit = withUnfold(inData) {
        uuid = loadUuid4(0)
        byte = loadByte(16)
        short = loadShort(17)
        int = loadInt(19)
        long = loadLong(23)
        float = loadFloat(27)
        double = loadDouble(31)
        uByte = loadUByte(39)
        uShort = loadUShort(40)
        uInt = loadUInt(42)
        uLong = loadULong(46)
    }
}


class ExampleTest {

    @Test
    fun tryObject() {
        val to1 = TestObject(uuid4())
        val buf = DataBuffer()
        ObjectType(to1).enfoldToStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldFromStream(buf) { TestObject() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun tryStruct() {
        val to1 = TestStruct(uuid4())
        val buf = DataBuffer()
        StructType(to1).enfoldToStream(buf)
        buf.flip()
        val to2 = StructType.unfoldFromStream(buf) { TestStruct() }.value

        assertEquals(to1, to2)
    }
}