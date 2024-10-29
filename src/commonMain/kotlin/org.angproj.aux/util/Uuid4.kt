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

import org.angproj.aux.buf.asWrapped
import org.angproj.aux.buf.wrap
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.rand.AbstractSmallRandom
import org.angproj.aux.rand.InitializationVector
import org.angproj.aux.utf.Ascii
import org.angproj.aux.util.Hex.lowerToHex
import org.angproj.aux.util.Hex.upperToHex
import kotlin.native.concurrent.ThreadLocal

public class Uuid4(private val uuid: Binary) {

    public constructor() : this(generate())

    init {
        require(uuid.limit == 16) { "Wrong size of data!" }
        require(uuid.retrieveByte(6).toInt() and 0xf0 == 0x40) { "Not UUID version 4! Missing version" }
        require(uuid.retrieveByte(8).toInt() and 0xc0 == 0x80) { "Not UUID version 4! Missing variant" }
    }

    private val hex: Text by lazy {
        BufMgr.txt(36).apply {
            val text = asBinary().asWrapped()
            val data = uuid.asWrapped()
            val hyphen = Ascii.PRNT_HYPHEN.cp.toCodePoint()
            var octet: Byte = 0
            "10101010-1010-1010-1010-101010101010".forEach {
                when (it) {
                    '1' -> { octet = data.readByte(); text.writeGlyph(octet.upperToHex<Int>().toCodePoint()) }
                    '0' -> text.writeGlyph(octet.lowerToHex<Int>().toCodePoint())
                    else -> text.writeGlyph(hyphen)
                }
            }
        }
    }

    public fun asBinary(): Binary = if (!isNull()) uuid else error("Null object immutable")

    public fun toText(): Text = hex

    override fun toString(): String = hex.toByteArray().decodeToString().uppercase()

    public override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as Uuid4
        return uuid == other.uuid
    }

    public override fun hashCode(): Int = uuid.hashCode()

    @ThreadLocal
    protected companion object : AbstractSmallRandom(
        binOf(16).also { InitializationVector.realTimeGatedEntropy(it) }
    ) {

        private var counter: Int = 0

        init {
            revitalize()
        }

        private fun revitalize() {
            binOf(16).useWith {
                InitializationVector.realTimeGatedEntropy(it)
                reseed(it)
                counter = 0
            }
        }

        private fun generate(): Binary {
            if (counter.floorMod(Int.MAX_VALUE) == 0) revitalize()
            else counter++

            return binOf(16).wrap {
                writeInt(round())
                writeInt(((round().toLong() and 0xffff0fff) or 0x4000).toInt().asBig())
                writeInt(((round().toLong() and 0x3fffffff) or -0x80000000).toInt().asBig())
                writeInt(round())
            }
        }
    }
}

public fun uuid4(): Uuid4 = Uuid4()

public fun uuid4Of(data: Binary): Uuid4 = Uuid4(data)

public fun Uuid4.isNull(): Boolean = NullObject.uuid4 === this

private val nullUuid4 = uuid4Of(
    byteArrayOf(
        0, 0, 0, 0,
        0, 0, 64, 0,
        -128, 0, 0, 0,
        0, 0, 0, 0
    ).toBinary()
)
public val NullObject.uuid4: Uuid4
    get() = nullUuid4