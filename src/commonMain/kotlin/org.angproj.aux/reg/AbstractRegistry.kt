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
package org.angproj.aux.reg

public abstract class AbstractRegistry<I : RegistryItem, P : RegistryProxy> : Registry<I, P> {

    private val itemCodes: MutableSet<Int> = mutableSetOf()

    private val itemHandles: MutableMap<Int, I> = mutableMapOf()

    private val itemMap: MutableMap<String, Int> = mutableMapOf()

    private val itemProtection: MutableSet<Int> = mutableSetOf()

    protected abstract fun wrapInProxy(item: I): P

    protected fun protect(handle: Int) {
        itemProtection.add(handle)
    }

    public override fun register(item: I): Int {
        val handle = item.identifier.hashCode()

        check(handle != 0) {
            "Unfortunate accident, this item's identifier '${item.identifier}' generates hashCode 0!"
        }
        check(!itemCodes.contains(handle)) {
            "Item already registered: ${item.identifier}, ${handle}"
        }

        itemCodes.add(handle)
        itemMap.put(item.identifier, handle)
        itemHandles.set(handle, item)

        item.initialize()

        return handle
    }

    public override fun unregister(handle: Int): I {
        check(itemCodes.contains(handle)) { "Item not registered: $handle" }
        check(!itemProtection.contains(handle)) { "Can't unregister protected item!" }

        val item = itemHandles[handle]
        itemCodes.remove(handle)
        itemMap.remove(item!!.identifier)
        itemHandles.remove(handle)
        item.finalize()

        return item
    }

    public override fun receive(handle: Int): P {
        check(itemCodes.contains(handle)) { "Item not registered: $handle" }
        return wrapInProxy(itemHandles.get(handle)!!)
    }

    public override fun lookup(identifier: String): Int = if (itemMap.contains(identifier)) itemMap[identifier]!! else 0
}