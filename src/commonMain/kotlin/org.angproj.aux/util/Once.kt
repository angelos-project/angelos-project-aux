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

@Suppress("UNCHECKED_CAST")
public class Once<E: Any> : ReadWriteProperty<Any, E> {

    private var value: E = NullObject.any as E

    override fun getValue(thisRef: Any, property: KProperty<*>): E {
        require(!value.isNull()) { "Property ${property.name} of $thisRef is not available yet." }
        return value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: E) {
        require(value.isNull()) { "Property ${property.name} of $thisRef is already set." }
        this.value = value
    }
}