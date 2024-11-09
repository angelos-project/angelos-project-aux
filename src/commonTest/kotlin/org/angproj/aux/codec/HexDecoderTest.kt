package org.angproj.aux.codec

import org.angproj.aux.TestInformationStub
import org.angproj.aux.toTextBuffer
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.time.measureTime


class HexDecoderTest {
    @Test
    fun testDecode() {
        assertFailsWith<IllegalStateException> {
            val tb = "0x0123456789ABCDEFabcdef".toTextBuffer()
            HexDecoder().decode(tb)
        }

        try {
            val tb = "0123456789ABCDEFabcdef".toTextBuffer()
            HexDecoder().decode(tb)
        } catch (_: Exception) {
            assertTrue { false }
        }

        try {
            measureTime {
                val tb = TestInformationStub.data.toTextBuffer()
                HexDecoder().decode(tb)
            }.let { println(it) }
        } catch (_: Exception) {
            assertTrue { false }
        }
    }
}