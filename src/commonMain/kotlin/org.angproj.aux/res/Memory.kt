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
package org.angproj.aux.res

import org.angproj.aux.util.Reifiable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class Memory: Cleanable {
    public val size: Int
    public val ptr: Long
}

public expect fun allocateMemory(size: Int): Memory

internal expect inline fun <reified T: Reifiable> Memory.speedLongGet(index: Long): Long

internal expect inline fun <reified T: Reifiable> Memory.speedLongSet(index: Long, value: Long)