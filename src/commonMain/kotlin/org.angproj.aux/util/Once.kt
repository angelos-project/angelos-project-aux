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

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

public class Once<E: Any> : ReadWriteProperty<Any, E> {

    private var value: E? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): E {
        require(value != null) { "Property ${property.name} of $thisRef is not available yet." }
        return value as E
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: E) {
        require(this.value == null) { "Property ${property.name} of $thisRef is already set." }
        this.value = value
    }

    public fun isNull(): Boolean = value === null
}