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

public enum class DataSize(public val size: Int) {
    UNKNOWN(-1),
    _32B(32),
    _64B(_32B.size * 2),
    _128B(_64B.size * 2),
    _256B(_128B.size * 2),
    _512B(_256B.size * 2),
    _1K(_512B.size * 2),
    _2K(_1K.size * 2),
    _4K(_2K.size * 2),
    _8K(_4K.size * 2),
    _16K(_8K.size * 2),
    _32K(_16K.size * 2),
    _64K(_32K.size * 2),
    _128K(_64K.size * 2),
    _256K(_128K.size * 2),
    _512K(_256K.size * 2),
    _1M(_512K.size * 2),
    _2M(_1M.size * 2),
    _4M(_2M.size * 2),
    _8M(_4M.size * 2),
    _16M(_8M.size * 2),
    _32M(_16M.size * 2),
    _64M(_32M.size * 2),
    _128M(_64M.size * 2),
    _256M(_128M.size * 2),
    _512M(_256M.size * 2),
    _1G(_512M.size * 2),
}