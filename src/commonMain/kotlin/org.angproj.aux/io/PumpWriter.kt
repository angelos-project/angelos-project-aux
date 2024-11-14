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
 * The [PumpWriter] receives data through segments which it has to copy into whatever
 * drainage is implemented. It reads the full segments up to its limits and return
 * the number of copied bytes each time. If a segment has a limit set under its
 * capacity it is a sign that it has reached EOF which simply is noted as the implementation
 * has to deal with it. If the [PumpReader]s inner state is closed or ... the method returns -1
 * */

/**
 * A [PumpWriter] must never send partial data if it indicates itself as stale.
 * */
public interface PumpWriter {

    public val inputCount: Long

    /**
     * Stale mode means that the writer is currently busy digesting data.
     * */
    public val inputStale: Boolean

    public fun write(data: Segment<*>): Int
}