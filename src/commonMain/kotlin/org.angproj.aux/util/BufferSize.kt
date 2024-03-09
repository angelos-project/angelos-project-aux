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

public enum class BufferSize(public val size: Int) {
    _1K(1024),
    _2K(_1K.size * 2),
    _4K(_2K.size * 2),
    _8K(_4K.size * 2),
    _16K(_8K.size * 2),
    _32K(_16K.size * 2),
    _64K(_32K.size * 2),
    _128K(_64K.size * 2),
    _256K(_128K.size * 2),
    _512K(_256K.size * 2),
    _1M(_512K.size * 2)
}