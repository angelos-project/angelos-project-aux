package org.angproj.aux.codec

import org.angproj.aux.TestInformationStub
import org.angproj.aux.toTextBuffer
import kotlin.test.Test


class HexDecoderTest {
    @Test
    fun testStreamPullClose() {
        val tb = TestInformationStub.data.toTextBuffer()
        println(HexDecoder().decode(tb).limit)
    }
}