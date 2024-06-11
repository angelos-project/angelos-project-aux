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
package org.angproj.aux.utf

public interface UtfBlock {
    public val name: String
    public val meta: String
    public val range: IntRange
    public val noCtrl: Boolean
    public val noUse: List<Int>

    public fun isValid(codePoint: CodePoint): Boolean = codePoint.value in range && codePoint.value !in noUse
}