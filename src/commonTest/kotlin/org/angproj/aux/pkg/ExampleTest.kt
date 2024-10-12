package org.angproj.aux.pkg

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Text
import org.angproj.aux.io.Writable
import org.angproj.aux.io.toText
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.uuid4
import kotlin.test.Test


data class TestObject(
    var uuid: Uuid4 = NullObject.uuid4,
    var text: Text = "Hello, world!".toText()
): Packageable {

    override val foldFormat: FoldFormat = FoldFormat.STREAM

    override fun foldSize(foldFormat: FoldFormat): Long = withFoldSize(foldFormat) {
        listOf(
            sizeOf(uuid),
            sizeOf(text),
        ).sum()
    }

    override fun enfold(outStream: Writable): Unit = withEnfold(outStream) {
        saveUuid4(uuid)
        saveString(text)
    }

    override fun unfold(inStream: Readable): Unit = withUnfold(inStream) {
        uuid = loadUuid4()
        text = loadString()
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

    override val foldFormat: FoldFormat = FoldFormat.STREAM

    override fun foldSize(foldFormat: FoldFormat): Long = withFoldSize(foldFormat){
        listOf(
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
        ).sum()
    }

    override fun enfold(outStream: Writable): Unit = withEnfold(outStream) {
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
    }

    override fun unfold(inStream: Readable): Unit = withUnfold(inStream) {
        uuid = loadUuid4()
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
    }
}


class ExampleTest {

    @Test
    fun work() {

    }
}