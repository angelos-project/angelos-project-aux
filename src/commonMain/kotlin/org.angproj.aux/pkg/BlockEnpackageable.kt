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
import org.angproj.aux.pkg.arb.Uuid4Type
import org.angproj.aux.pkg.mem.*
import org.angproj.aux.pkg.prime.*

public interface BlockEnpackageable: Packageable {
    public fun enfold(outData: Storable, offset: Int)

    public companion object {
        protected fun saveByte(outData: Storable, offset: Int, type: ByteType): Long = type.enfold(outData, offset)
        protected fun saveShort(outData: Storable, offset: Int, type: ShortType): Long = type.enfold(outData, offset)
        protected fun saveInt(outData: Storable, offset: Int, type: IntType): Long = type.enfold(outData, offset)
        protected fun saveLong(outData: Storable, offset: Int, type: LongType): Long = type.enfold(outData, offset)
        protected fun saveFloat(outData: Storable, offset: Int, type: FloatType): Long = type.enfold(outData, offset)
        protected fun saveDouble(outData: Storable, offset: Int, type: DoubleType): Long = type.enfold(outData, offset)
        protected fun saveUShort(outData: Storable, offset: Int, type: UShortType): Long = type.enfold(outData, offset)
        protected fun saveUInt(outData: Storable, offset: Int, type: UIntType): Long = type.enfold(outData, offset)
        protected fun saveULong(outData: Storable, offset: Int, type: ULongType): Long = type.enfold(outData, offset)

        protected fun saveShortArray(outData: Storable, offset: Int, type: ShortArrayType): Long = type.enfold(outData, offset)
        protected fun saveIntArray(outData: Storable, offset: Int, type: IntArrayType): Long = type.enfold(outData, offset)
        protected fun saveLongArray(outData: Storable, offset: Int, type: LongArrayType): Long = type.enfold(outData, offset)
        protected fun saveFloatArray(outData: Storable, offset: Int, type: FloatArrayType): Long = type.enfold(outData, offset)
        protected fun saveDoubleArray(outData: Storable, offset: Int, type: DoubleArrayType): Long = type.enfold(outData, offset)

        protected fun saveUuid4(outData: Storable, offset: Int, type: Uuid4Type): Long = type.enfold(outData, offset)
    }
}