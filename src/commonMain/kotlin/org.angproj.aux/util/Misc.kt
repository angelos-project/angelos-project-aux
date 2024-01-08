/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.aux.num.AbstractBigInt
import org.angproj.aux.num.BigInt
import org.angproj.aux.num.MutableBigInt
import kotlin.math.absoluteValue

public fun uuid4(): Uuid4 = Uuid4()

public fun uuid4Of(data: ByteArray): Uuid4 = Uuid4(data)

public fun bigIntOf(value: IntArray): BigInt =
    AbstractBigInt.fromIntArray(value) { a, b -> BigInt(a, b) }

public fun bigIntOf(value: ByteArray): BigInt =
    AbstractBigInt.fromByteArray(value) { a, b -> BigInt(a, b) }

public fun bigIntOf(value: Long): BigInt = AbstractBigInt.fromLong(value) { a, b -> BigInt(a, b) }

public fun mutableBigIntOf(value: IntArray): MutableBigInt =
    AbstractBigInt.fromIntArray(value) { a, b -> MutableBigInt(a, b) }

public fun mutableBigIntOf(value: ByteArray): MutableBigInt =
    AbstractBigInt.fromByteArray(value) { a, b -> MutableBigInt(a, b) }

public fun mutableBigIntOf(value: Long): MutableBigInt =
    AbstractBigInt.fromLong(value) { a, b -> MutableBigInt(a, b) }

public fun encodeToHex(data: ByteArray): String = BinHex.encodeToHex(data)

public fun decodeFromHex(hex: String): ByteArray = BinHex.decodeToBin(hex)

public fun Int.floorMod(other: Int): Int = this.absoluteValue.mod(other.absoluteValue)
public fun Long.floorMod(other: Long): Long = this.absoluteValue.mod(other.absoluteValue)
