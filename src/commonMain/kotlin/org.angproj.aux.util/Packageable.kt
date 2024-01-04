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

import org.angproj.aux.util.io.Readable
import org.angproj.aux.util.io.Storable
import org.angproj.aux.util.io.Writable
import org.angproj.io.buf.Retrievable

public interface Packageable : EndianAware {

    public fun enfold(outStream: Writable)

    public fun enfold(outBlock: Storable)

    public fun unfold(inStream: Readable)

    public fun unfold(inBlock: Retrievable)

    public fun foldSize(): Long
}