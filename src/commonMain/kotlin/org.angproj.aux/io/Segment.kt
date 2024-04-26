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

public abstract class Segment(size: Int, idxSize: TypeSize): AbstractSpeedCopy(size, idxSize),  ByteString {
    public fun Long.getLeftSide(offset: Int, size: Int): Long = (
            this shl ((size - ByteString.longSize - offset) * 8))

    public fun Long.getRightSide(offset: Int, size: Int): Long = (
            this ushr ((ByteString.longSize - size - ByteString.longSize - offset) * 8))
}