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
package org.angproj.aux.util

import org.angproj.io.buf.Retrievable

public interface Packageable : EndianAware {

    public fun enfold(outStream: org.angproj.aux.io.Writable)

    public fun enfold(outBlock: org.angproj.aux.io.Storable)

    public fun unfold(inStream: org.angproj.aux.io.Readable)

    public fun unfold(inBlock: Retrievable)

    public fun foldSize(): Long
}