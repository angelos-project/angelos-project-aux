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
package org.angproj.aux.com

public interface Limitable: Sized {
    /**
     * The limited use of an element between 0 and size.
     * */
    public val limit: Int

    /**
     * Sets a new limit.
     * */
    public fun limitAt(newLimit: Int)

    public fun clear() { limitAt(size) }
}