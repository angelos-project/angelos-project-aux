/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.aux.pkg.arb

import org.angproj.aux.TestInformationStub
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.num.BigInt
import org.angproj.aux.num.bigInt
import org.angproj.aux.num.bigIntOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.util.BinHex
import org.angproj.aux.util.NullObject
import kotlin.test.Test
import kotlin.test.assertEquals


data class BigIntTestObjectPackage(
    var bigInt: BigInt = NullObject.bigInt
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(bigInt),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveBigInt(bigInt) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { bigInt = loadBigInt() }
}

data class BigIntTestObjectPackageable(
    var bigInt: BigInt = NullObject.bigInt
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(bigInt),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveBigInt(bigInt) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { bigInt = loadBigInt() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}


class BigIntTypeTest {

    private val testBigInt = bigIntOf(BinHex.decodeToBin(TestInformationStub.number1)) //BigInt.minusOne

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = BigIntTestObjectPackage(testBigInt)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { BigIntTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = BigIntTestObjectPackageable(testBigInt)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { BigIntTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStream() {
        val texts = listOf(
            TestInformationStub.number1,
            TestInformationStub.number2,
            TestInformationStub.number3
        )
        val stream = BinaryBuffer()

        texts.forEach {
            stream.clear()
            val type = BigIntType(bigIntOf(BinHex.decodeToBin(it)))
            type.enfoldStream(stream)
            stream.flip()
            assertEquals(type.foldSize(FoldFormat.STREAM), stream.limit)

            val retrieved = BigIntType.unfoldStream(stream)
            assertEquals(type, retrieved)
        }
    }
}