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
import org.angproj.aux.rand.Entropy
import org.angproj.aux.utf.Ascii
import kotlin.native.concurrent.ThreadLocal

public class Uuid4(private val uuid: Binary) {

    public constructor() : this(generate())

    init {
        require(uuid.limit == 16) { "Wrong size of data!" }
        require(uuid.retrieveByte(6).toInt() and 0xf0 == 0x40) { "Not UUID version 4! Missing version" }
        require(uuid.retrieveByte(8).toInt() and 0xc0 == 0x80) { "Not UUID version 4! Missing variant" }
    }

    private val hex: Text by lazy {
        withUtility {
            BufMgr.txt(36).apply {
                val text = asBinary().asWrapped()
                val data = uuid.asWrapped()
                val hyphen = Ascii.PRNT_HYPHEN.cp.toCodePoint()
                var octet: Byte = 0
                "10101010-1010-1010-1010-101010101010".forEach {
                    when (it) {
                        '1' -> { octet = data.readByte(); text.writeGlyph(octet.upperToHex()) }
                        '0' -> text.writeGlyph(octet.lowerToHex())
                        else -> text.writeGlyph(hyphen)
                    }
                }
            }
        }
    }

    public fun asBinary(): Binary = if (!isNull()) uuid else error("Null object immutable")

    public fun toText(): Text = hex

    override fun toString(): String = toText().toByteArray().decodeToString().uppercase()

    public override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as Uuid4
        return uuid == other.uuid
    }

    public override fun hashCode(): Int = uuid.hashCode()

    @ThreadLocal
    public companion object : AbstractSmallRandom(
        binOf(16).also { Entropy.realTimeGatedEntropy(it) }
    ), PumpReader {

        private var counter: Long = 0

        private var _count: Long = 0
        override val outputCount: Long
            get() = _count + counter

        override val outputStale: Boolean = false

        private fun revitalize() {
            binOf(16).useWith {
                Entropy.realTimeGatedEntropy(it)
                reseed(it)
                _count += counter
                counter = 0
            }
        }

        // The init{} is moved down because of error in IR for JS
        init { revitalize() }

        private fun generate(): Binary {
            if (counter >= Int.MAX_VALUE) revitalize()
            counter += 4

            return binOf(16).wrap {
                writeInt(round())
                writeInt(((round().toLong() and 0xffff0fff) or 0x4000).toInt().asNet())
                writeInt(((round().toLong() and 0x3fffffff) or -0x80000000).toInt().asNet())
                writeInt(round())
            }
        }

        public override fun read(data: Segment<*>): Int {
            require(data.limit.floorMod(TypeSize.int) == 0)

            if (counter > Int.MAX_VALUE) revitalize()
            counter += data.limit / TypeSize.int

            (0 until data.limit step TypeSize.int).forEach { data.setInt(it, round()) }

            return data.limit
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