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

import kotlin.math.absoluteValue

public fun encodeToHex(data: ByteArray): String = BinHex.encodeToHex(data)

public fun decodeFromHex(hex: String): ByteArray = BinHex.decodeToBin(hex)

public fun Int.floorMod(other: Int): Int = this.absoluteValue.mod(other.absoluteValue)
public fun Long.floorMod(other: Long): Long = this.absoluteValue.mod(other.absoluteValue)

public fun bitArrayOf(value: ByteArray): BitArray = BitArray(value)

public inline fun <reified T: Reifiable> chunkLoop(index: Int, length: Int, slice: Int, action: (Int) -> Unit): Int {
    val steps = (length - index) / slice
    val size = steps * slice
    if (steps > 0) (index until (index + size) step slice).forEach { action(it) }
    return index + size
}