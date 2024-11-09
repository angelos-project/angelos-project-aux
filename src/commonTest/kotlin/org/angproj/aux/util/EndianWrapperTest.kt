package org.angproj.aux.util

import org.angproj.aux.TestInformationStub.refDouble
import org.angproj.aux.TestInformationStub.refFloat
import org.angproj.aux.TestInformationStub.refInt
import org.angproj.aux.TestInformationStub.refLong
import org.angproj.aux.TestInformationStub.refShort
import org.angproj.aux.TestInformationStub.refUInt
import org.angproj.aux.TestInformationStub.refULong
import org.angproj.aux.TestInformationStub.refUShort
import kotlin.test.*


object EndianNegativeContext : EndianContext {
    override val endianCtx: Endian = if(Endian.native.asLittleIfUnknown()) Endian.BIG else Endian.LITTLE

    val testShort: Short = refShort
    val testUShort: UShort = refUShort

    val testLong: Long = refLong
    val testULong: ULong = refULong

    val testInt: Int = refInt
    val testUInt: UInt = refUInt

    val testFloat: Float = refFloat
    val testDouble: Double = refDouble
}

object EndianSameContext : EndianContext {
    override val endianCtx: Endian = if(Endian.native.asLittleIfUnknown()) Endian.LITTLE else Endian.BIG

    val testShort: Short = refShort
    val testUShort: UShort = refUShort

    val testLong: Long = refLong
    val testULong: ULong = refULong

    val testInt: Int = refInt
    val testUInt: UInt = refUInt

    val testFloat: Float = refFloat
    val testDouble: Double = refDouble
}


class EndianWrapperTest {

    @Test
    fun getSwap() {
        withEndianContextAware(EndianSameContext) { assertFalse { swap } }
        withEndianContextAware(EndianNegativeContext) { assertTrue { swap } }
    }

    @Test
    fun swapAdjust() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testShort.swapAdjust(), refShort) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testShort.swapAdjust(), refShort.swapEndian()) }
    }

    @Test
    fun testSwapAdjust() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testUShort.swapAdjust(), refUShort) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testUShort.swapAdjust(), refUShort.swapEndian()) }
    }

    @Test
    fun testSwapAdjust1() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testInt.swapAdjust(), refInt) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testInt.swapAdjust(), refInt.swapEndian()) }
    }

    @Test
    fun testSwapAdjust2() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testUInt.swapAdjust(), refUInt) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testUInt.swapAdjust(), refUInt.swapEndian()) }
    }

    @Test
    fun testSwapAdjust3() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testLong.swapAdjust(), refLong) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testLong.swapAdjust(), refLong.swapEndian()) }
    }

    @Test
    fun testSwapAdjust4() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testULong.swapAdjust(), refULong) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testULong.swapAdjust(), refULong.swapEndian()) }
    }

    @Test
    fun testSwapAdjust5() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testFloat.swapAdjust(), refFloat) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testFloat.swapAdjust(), refFloat.swapEndian()) }
    }

    @Test
    fun testSwapAdjust6() {
        withEndianContextAware(EndianSameContext) { assertEquals(one.testDouble.swapAdjust(), refDouble) }
        withEndianContextAware(EndianNegativeContext) { assertEquals(one.testDouble.swapAdjust(), refDouble.swapEndian()) }
    }

    @Test
    fun swapWrapped() {
        val wrap = EndianNegativeContext.swapWrapped(EndianNativeContext)
        wrap.swap {
            assertTrue { swap }
            assertEquals(one.testShort.swapAdjust(), refShort.swapEndian())
            assertEquals(one.testUShort.swapAdjust(), refUShort.swapEndian())
            assertEquals(one.testInt.swapAdjust(), refInt.swapEndian())
            assertEquals(one.testUInt.swapAdjust(), refUInt.swapEndian())
            assertEquals(one.testLong.swapAdjust(), refLong.swapEndian())
            assertEquals(one.testULong.swapAdjust(), refULong.swapEndian())
            assertEquals(one.testFloat.swapAdjust(), refFloat.swapEndian())
            assertEquals(one.testDouble.swapAdjust(), refDouble.swapEndian())
        }
    }
}