package org.angproj.aux.io

import org.angproj.aux.TestInformationStub
import org.angproj.aux.util.NullObject
import kotlin.test.*


class BinaryTest: MemBlockTest<Binary>() {

    override val txtLen = TestInformationStub.refArray.size

    override fun setInput(): Binary = TestInformationStub.refArray.toBinary()

    @Test
    fun retrieveStoreByte() {
        val bin = setInput()
        (0 until bin.limit step 1).forEach { bin.storeByte(it, TestInformationStub.refByte) }
        (0 until bin.limit step 1).forEach { assertEquals(bin.retrieveByte(it), TestInformationStub.refByte) }
    }

    @Test
    fun retrieveStoreUByte() {
        val bin = setInput()
        (0 until bin.limit step 1).forEach { bin.storeUByte(it, TestInformationStub.refUByte) }
        (0 until bin.limit step 1).forEach { assertEquals(bin.retrieveUByte(it), TestInformationStub.refUByte) }
    }

    @Test
    fun retrieveStoreShort() {
        val bin = setInput()
        (0 until bin.limit step 2).forEach { bin.storeShort(it, TestInformationStub.refShort) }
        (0 until bin.limit step 2).forEach { assertEquals(bin.retrieveShort(it), TestInformationStub.refShort) }
    }

    @Test
    fun retrieveStoreUShort() {
        val bin = setInput()
        (0 until bin.limit step 2).forEach { bin.storeUShort(it, TestInformationStub.refUShort) }
        (0 until bin.limit step 2).forEach { assertEquals(bin.retrieveUShort(it), TestInformationStub.refUShort) }
    }

    @Test
    fun retrieveStoreInt() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeInt(it, TestInformationStub.refInt) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveInt(it), TestInformationStub.refInt) }
    }

    @Test
    fun retrieveStoreUInt() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeUInt(it, TestInformationStub.refUInt) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveUInt(it), TestInformationStub.refUInt) }
    }

    @Test
    fun retrieveStoreLong() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeLong(it, TestInformationStub.refLong) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveLong(it), TestInformationStub.refLong) }
    }

    @Test
    fun retrieveStoreULong() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeULong(it, TestInformationStub.refULong) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveULong(it), TestInformationStub.refULong) }
    }

    @Test
    fun retrieveStoreFloat() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeFloat(it, TestInformationStub.refFloat) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveFloat(it), TestInformationStub.refFloat) }
    }

    @Test
    fun retrieveStoreDouble() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeDouble(it, TestInformationStub.refDouble) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveDouble(it), TestInformationStub.refDouble) }
    }

    @Test
    fun testNullBinary() {
        assertTrue(NullObject.binary.isNull())
        assertFalse(Binary().isNull())
    }
}