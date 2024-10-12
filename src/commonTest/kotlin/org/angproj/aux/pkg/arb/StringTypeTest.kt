package org.angproj.aux.pkg.arb

import org.angproj.aux.TestInformationStub
import org.angproj.aux.io.toText
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.util.DataBuffer
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
        val stream = DataBuffer()

        texts.forEach {
            stream.reset()
            val type = StringType(it)
            type.enfoldToStream(stream)
            stream.flip()
            assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM).toInt())

            val retrieved = StringType.unfoldFromStream(stream)
            assertContentEquals(type.value, retrieved.value)
        }
    }
}