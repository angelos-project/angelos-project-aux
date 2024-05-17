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

import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment

public object Pump: PumpReader {
    override fun read(data: Segment): Int {
        throw UnsupportedOperationException("No readable pump set.")
    }

    override fun readInto(data: Segment, size: Int): Int {
        throw UnsupportedOperationException("No readable pump set.")
    }
}