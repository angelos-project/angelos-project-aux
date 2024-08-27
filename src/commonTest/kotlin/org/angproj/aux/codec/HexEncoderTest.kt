package org.angproj.aux.codec

import org.angproj.aux.io.Binary
import org.angproj.aux.sec.SecureRandom
import kotlin.test.Test


class HexEncoderTest {
    @Test
    fun testStreamPullClose() {
        val bin = Binary(2048)
        SecureRandom.read(bin.segment)
        println(HexEncoder().encode(bin).limit)
    }
}