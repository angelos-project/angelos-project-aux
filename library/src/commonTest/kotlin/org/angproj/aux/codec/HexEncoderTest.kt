package org.angproj.aux.codec

import org.angproj.aux.io.binOf
import org.angproj.aux.sec.SecureRandom
import kotlin.test.Test


class HexEncoderTest {
    @Test
    fun testStreamPullClose() {
        val bin = binOf(2048)
        SecureRandom.read(bin._segment)
        println(HexEncoder().encode(bin).limit)
    }
}