package org.angproj.aux.pkg.arb

import org.angproj.aux.TestInformationStub
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.num.bigIntOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.util.BinHex
import kotlin.test.Test
import kotlin.test.assertEquals


class BigIntTypeTest {

    @Test
    fun enfoldToStream() {
        val texts = listOf(
            TestInformationStub.number1,
            TestInformationStub.number2,
            TestInformationStub.number3
        )
        val stream = BinaryBuffer()

        texts.forEach {
            stream.clear()
            val type = BigIntType(bigIntOf(BinHex.decodeToBin(it)))
            type.enfoldToStream(stream)
            stream.flip()
            assertEquals(type.foldSize(FoldFormat.STREAM).toInt(), stream.limit)

            val retrieved = BigIntType.unfoldFromStream(stream)
            assertEquals(type, retrieved)
        }
    }
}