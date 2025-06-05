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
package org.angproj.aux.mem

import org.angproj.aux.io.Bytes
import org.angproj.aux.io.DataSize

public object Default: FreeAlloc<Bytes> {
    override fun allocate(size: Int): Bytes = Bytes(size, this)

    override fun allocate(dataSize: DataSize): Bytes = allocate(dataSize.size)

    override fun recycle(segment: Bytes) { }
}