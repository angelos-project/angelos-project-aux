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

public enum class Endian {
    BIG, LITTLE, UNKNOWN;

    public fun isBig(): Boolean = this == BIG
    public fun isLittle(): Boolean = this == LITTLE
    public fun asLittleIfUnknown(): Boolean = this in assumeLittle

    public companion object {
        private val assumeLittle = listOf(UNKNOWN, LITTLE)

        public val native: Endian = getCurrentEndian()
        public val network: Endian = BIG
    }
}