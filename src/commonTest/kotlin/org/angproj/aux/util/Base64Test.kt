package org.angproj.aux.util

import org.angproj.aux.sec.SecureFeed
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test

class Base64Test {

    @Test
    fun encode() {
        val buffer = DataBuffer()
        SecureFeed.read(buffer.asByteArray())
        println(buffer.asByteArray().decodeToString())
    }

    @Test
    fun decode() {
    }
}