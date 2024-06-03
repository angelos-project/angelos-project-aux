package org.angproj.aux.utf

import kotlin.test.Test
import kotlin.test.assertEquals

class SequenceTypeTest {

    @Test
    fun testExtract() {
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.START_ONE_LONG, 0xFF.toByte()).countLeadingZeroBits(), 7)
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.FOLLOW_DATA, 0xFF.toByte()).countLeadingZeroBits(), 6)
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.START_TWO_LONG, 0xFF.toByte()).countLeadingZeroBits(), 5)
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.START_THREE_LONG, 0xFF.toByte()).countLeadingZeroBits(), 4)
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.START_FOUR_LONG, 0xFF.toByte()).countLeadingZeroBits(), 3)
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.START_FIVE_LONG, 0xFF.toByte()).countLeadingZeroBits(), 2)
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.START_SIX_LONG, 0xFF.toByte()).countLeadingZeroBits(), 1)
        assertEquals(Int.SIZE_BITS - SequenceType.extract(SequenceType.ILLEGAL, 0xFF.toByte()).countLeadingZeroBits(), 0)
    }
}