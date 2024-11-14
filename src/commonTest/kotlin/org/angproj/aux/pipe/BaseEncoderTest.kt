package org.angproj.aux.pipe

import org.angproj.aux.TestInformationStub
import org.angproj.aux.io.asBinary
import org.angproj.aux.io.toBinary
import org.angproj.aux.io.toByteArray
import org.angproj.aux.io.toText
import org.angproj.aux.util.BaseEncoder
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Base64 test-vectors taken from RFC 4648
 * */
class BaseEncoderTest {

    @Test
    fun testEncode() {
        val enc = BaseEncoder()

        println(enc.encode("".toText().asBinary()).toByteArray().decodeToString())
        println(enc.encode("f".toText().asBinary()).toByteArray().decodeToString())
        println(enc.encode("fo".toText().asBinary()).toByteArray().decodeToString())
        println(enc.encode("foo".toText().asBinary()).toByteArray().decodeToString())
        println(enc.encode("foob".toText().asBinary()).toByteArray().decodeToString())
        println(enc.encode("fooba".toText().asBinary()).toByteArray().decodeToString())
        println(enc.encode("foobar".toText().asBinary()).toByteArray().decodeToString())

        println(enc.encode(TestInformationStub.refByteArray.toBinary()).asBinary().toByteArray().decodeToString())

        assertEquals(enc.encode("".toText().asBinary()).toByteArray().decodeToString(),"")
        assertEquals(enc.encode("f".toText().asBinary()).toByteArray().decodeToString(),"Zg==")
        assertEquals(enc.encode("fo".toText().asBinary()).toByteArray().decodeToString(),"Zm8=")
        assertEquals(enc.encode("foo".toText().asBinary()).toByteArray().decodeToString(),"Zm9v")
        assertEquals(enc.encode("foob".toText().asBinary()).toByteArray().decodeToString(),"Zm9vYg==")
        assertEquals(enc.encode("fooba".toText().asBinary()).toByteArray().decodeToString(),"Zm9vYmE=")
        assertEquals(enc.encode("foobar".toText().asBinary()).toByteArray().decodeToString(),"Zm9vYmFy")
    }
}