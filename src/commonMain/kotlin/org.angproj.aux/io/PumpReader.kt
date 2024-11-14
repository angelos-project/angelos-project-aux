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

/**
 * The [PumpReader] is given to a [PumpSource] so that data can be read from it.
 * It is necessary that the implementation of the reader is giving correct feedback,
 * it always ought to return the correct number of bytes copied into the segment
 * given to it, between 0 and limit, a read that yields lesser than what the limit
 * allows indicates a temporary eof. In case yielding lesser data, the responsibility
 * of setting a lower limit is taken care of by the [PumpSource] itself to avoid mistakes
 * by implementers and unnecessary debugging. In case the PumpReaders state is broken
 * it has to return -1 and with the current given segment totally untouched.
 * */
public interface PumpReader {

    public val outputCount: Long

    /**
     * Stale mode indicates that the reader is temporarily out of data.
     * */
    public val outputStale: Boolean

    /**
     * The read segments limit will be forcefully updated to that of the returned value of read bytes.
     * */
    public fun read(data: Segment<*>): Int
}