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

import kotlin.reflect.KProperty


public class Once<E: Any> {

    private lateinit var handle: E

    public operator fun getValue(thisRef: Any, property: KProperty<*>): E = handle

    public operator fun setValue(thisRef: Any, property: KProperty<*>, value: E) {
        if(::handle.isInitialized) throw IllegalStateException("Already initialized")
        handle = value
    }

    public fun isInitialized(): Boolean = ::handle.isInitialized
}