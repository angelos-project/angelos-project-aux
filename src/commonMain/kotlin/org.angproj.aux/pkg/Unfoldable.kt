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
package org.angproj.aux.pkg

import org.angproj.aux.io.Readable

public interface Unfoldable<E: Enfoldable> {
    public val foldFormat: FoldFormat
    public companion object {
        public fun getType(inStream: Readable, type: Convention) : Boolean = inStream.readShort() == type.type

        public fun getCount(inStream: Readable): Int = inStream.readInt()

    }
}