package org.angproj.aux.pkg.arb

import org.angproj.aux.TestInformationStub
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.toText
import org.angproj.aux.pkg.FoldFormat
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class StringTypeTest {

    @Test
    fun enfoldToStream() {
        val texts = listOf(
            TestInformationStub.lipsumShort.toText(),
            TestInformationStub.lipsumMedium.toText(),
            TestInformationStub.lipsumLong.toText()
        )
        val stream = BinaryBuffer()

        texts.forEach {
            stream.clear()
            val type = StringType(it)
            type.enfoldToStream(stream)
            stream.flip()
            assertEquals(type.foldSize(FoldFormat.STREAM).toInt(), stream.limit)

            val retrieved = StringType.unfoldFromStream(stream)
            assertEquals(type, retrieved)
        }
    }
}