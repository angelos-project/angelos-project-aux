package org.angproj.aux.util

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.sec.SecureFeed
import kotlin.test.Test

class Base64Test {

    @Test
    fun encode() {
        val buffer = BinaryBuffer()
        SecureFeed.read(buffer._segment)
        //println(buffer.asByteArray().decodeToString())
    }

    @Test
    fun decode() {
    }
}