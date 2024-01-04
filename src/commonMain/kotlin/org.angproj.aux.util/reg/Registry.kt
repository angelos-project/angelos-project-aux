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
package org.angproj.aux.util.reg

public interface Registry<I : RegistryItem, P : RegistryProxy> {

    public fun register(item: I): Int

    public fun unregister(handle: Int): I

    public fun receive(handle: Int): P

    public fun lookup(identifier: String): Int
}