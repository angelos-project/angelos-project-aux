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
package org.angproj.aux.buf

import org.angproj.aux.io.ByteString

public abstract class AbstractBufferType<E>(public val size: Int): BufferType<E> {
    protected fun realSizeCalc(size: Int): Int = (size / ByteString.longSize + if(size % ByteString.longSize != 0) 1 else 0) * ByteString.longSize
}