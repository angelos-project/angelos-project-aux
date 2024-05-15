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

import org.angproj.aux.buf.AbstractSpeedCopy

public class Model private constructor(
    size: Int, idxLimit: Int
): AbstractModel(size, idxLimit) {

    public constructor(size: Int) : this(size, size)

    override fun create(size: Int, idxLimit: Int): Model = Model(size, idxLimit)

    override fun <T: AbstractSpeedCopy> calculateInto(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
        innerCopy(dest as Model, destOff, idxFrom, idxTo)
    }

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}