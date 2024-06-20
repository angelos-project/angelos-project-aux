/**
 * Copyright (c) 2023-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.util

import org.angproj.aux.buf.copyOf
import org.angproj.aux.io.Binary
import org.angproj.aux.io.toBinary
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.rand.AbstractSmallRandom
import org.angproj.aux.rand.InitializationVector
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.util.Hex.bin2hex
import kotlin.native.concurrent.ThreadLocal

public class Uuid4 internal constructor(private val uuid: Binary) {

    public constructor() : this(generateByteArray())

    init {
        require(uuid.capacity == 16) { "Wrong size of data!" }
        require(uuid.retrieveByte(6).toInt() and 0xf0 == 64) { "Not UUID version 4!" }
    }

    private val hex: String by lazy {
        val _1 = uuid.retrieveInt(0).toUInt().toString(16)// BinHex.encodeToHex(arr.sliceArray(0 until 4))
        val _2 = uuid.retrieveShort(4).toUShort().toString(16) // BinHex.encodeToHex(arr.sliceArray(4 until 6))
        val _3 = uuid.retrieveShort(6).toUShort().toString(16) //BinHex.encodeToHex(arr.sliceArray(6 until 8))
        val _4 = uuid.retrieveShort(8).toUShort().toString(16) //BinHex.encodeToHex(arr.sliceArray(8 until 10))
        val _5 = (uuid.retrieveLong(8) and 0xffffff).toULong().toString(16) //BinHex.encodeToHex(arr.sliceArray(10 until 16))
        "$_1-$_2-$_3-$_4-$_5"
    }

    public fun asBinary(): Binary = uuid.asBinary()

    private fun hex(r: IntRange): String {
        var hex = ""
        r.forEach {
            val byte = uuid.retrieveByte(it)
            hex += byte.upperToHex<Reify>().value.toChar()
            hex += byte.lowerToHex<Reify>().value.toChar()
        }
        return hex
    }

    override fun toString(): String {
        var hex = ""
        hex += hex(0 until 4)
        hex += '-'
        hex += hex(4 until 6)
        hex += '-'
        hex += hex(6 until 8)
        hex += '-'
        hex += hex(8 until 10)
        hex += '-'
        hex += hex(10 until 16)
        return hex
    }

    @ThreadLocal
    protected companion object : AbstractSmallRandom(
        Binary(16).also { InitializationVector.realTimeGatedEntropy(it) }
    ) {

        private var counter: Int = 0

        init {
            revitalize()
        }

        private fun revitalize() {
            BufMgr.bin(16).useWith {
                InitializationVector.realTimeGatedEntropy(it)
                reseed(it)
                counter = 0
            }
        }

        private fun generateByteArray(): Binary {
            if (counter.floorMod(Int.MAX_VALUE) == 0) revitalize()
            else counter++

            val data = BufMgr.bin(16)

            data.storeInt(0, round())
            data.storeInt(4, round())
            data.storeInt(8, round())
            data.storeInt(12, round())

            var four = data.retrieveByte(6)
            four = four.flipOffFlag7()
            four = four.flipOnFlag6()
            four = four.flipOffFlag5()
            four = four.flipOffFlag4()
            data.storeByte(6, four)

            return data
        }
    }
}

internal inline fun<reified T: Reifiable> Byte.upperToHex(): CodePoint = CodePoint(bin2hex[toInt() ushr 4 and 0xf])

internal inline fun<reified T: Reifiable> Byte.lowerToHex(): CodePoint = CodePoint(bin2hex[toInt() and 0xf])

public fun uuid4(): Uuid4 = Uuid4()

public fun uuid4Of(data: Binary): Uuid4 = Uuid4(data)

public fun Uuid4.isNull(): Boolean = NullObject.uuid4 === this

private val nullUuid4 = uuid4Of(byteArrayOf(
    0, 0, 0, 0,
    0, 0, 64, 0,
    0, 0, 0, 0,
    0, 0, 0, 0
).toBinary())
public val NullObject.uuid4: Uuid4
    get() = nullUuid4