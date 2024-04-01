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
package org.angproj.aux.util

import kotlin.jvm.JvmStatic

/**
 * The goal is to avoid all type of NULL pointer exceptions, therefore it is necessary to provide a
 * null object instance wherever it is needed with empty initialized objects. Here are the base empty arrays
 * and objects of any type. The idea is to reduce the initialization of empty objects to reduce processing
 * power and memory consumption.
 * */
public object Null {

    public const val emptyString: String = ""

    @JvmStatic
    public val emptyByteArray: ByteArray = byteArrayOf()

    @JvmStatic
    public val emptyShortArray: ShortArray = shortArrayOf()

    @JvmStatic
    public val emptyIntArray: IntArray = intArrayOf()

    @JvmStatic
    public val emptyLongArray: LongArray = longArrayOf()

    @JvmStatic
    public val emptyFloatArray: FloatArray = floatArrayOf()

    @JvmStatic
    public val emptyDoubleArray: DoubleArray = doubleArrayOf()

    public val emptyUuid4: Uuid4 = uuid4Of(byteArrayOf(0,0,0,0,0,0,64,0,0,0,0,0,0,0,0,0))
}