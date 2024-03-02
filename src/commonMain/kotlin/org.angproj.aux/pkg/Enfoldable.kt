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

import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable

public interface Enfoldable {

    public val foldFormat: FoldFormat

    public fun foldSize(foldFormat: FoldFormat): Long

    public companion object {
        public const val TYPE_SIZE: Long = 2
        public const val COUNT_SIZE: Long = 4

        public fun setType(outStream: Writable, type: Convention) { outStream.writeShort(type.type) }

        public fun setCount(outStream: Writable, count: Int) { outStream.writeInt(count) }

        public fun setCount(outData: Storable, offset: Int, count: Int) { outData.storeInt(offset, count) }
    }
}