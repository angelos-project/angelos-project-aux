/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import kotlin.jvm.JvmInline

/**
 * TypePointer holds a native memory raw pointer while adding type assertion.
 *
 * Imported from the old buffer package and reimplemented
 *
 * DON'T MESS WITH RAW POINTERS!
 */
@JvmInline
public value class TypePointer(private val raw: Long) {
    public fun toPointer(): Long = raw
}