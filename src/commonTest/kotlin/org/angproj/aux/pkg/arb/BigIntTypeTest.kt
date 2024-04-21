package org.angproj.aux.pkg.arb

import org.angproj.aux.TestInformationStub
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.util.BinHex
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.num.bigIntOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BigIntTypeTest {

    @Test
    fun enfoldToStream() {
        val texts = listOf(
            TestInformationStub.number1,
            TestInformationStub.number2,
            TestInformationStub.number3
        )
        val stream = DataBuffer()

        texts.forEach {
            stream.reset()
            val type = BigIntType(bigIntOf(BinHex.decodeToBin(it)))
            type.enfoldToStream(stream)
            stream.flip()
            assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

            val retrieved = BigIntType.unfoldFromStream(stream)
            assertTrue { type.value.equals(retrieved.value) }
        }
    }
}