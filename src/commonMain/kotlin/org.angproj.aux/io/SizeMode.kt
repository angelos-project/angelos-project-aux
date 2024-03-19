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
package org.angproj.aux.io

/**
 * SizeMode describes the type of mode a Sizeable operates in. Three modes are available.
 *
 * FIXED: Always require a ByteArray of said size, never less, or more.
 * MAXIMUM: The ByteArray must never exceed said size, if not exact it is considered EOF.
 * ARBITRARY: There is no exact system to follow nor apply.
 * */
public enum class SizeMode {
    FIXED,
    MAXIMUM,
    ARBITRARY,
}