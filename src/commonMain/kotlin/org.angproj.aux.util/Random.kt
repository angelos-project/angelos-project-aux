/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.aux.util.rand.*
import org.angproj.aux.util.reg.AbstractRegistry

public object Random : AbstractRegistry<AbstractBufferedRandom, RandomProxy>() {

    init {
        val erHandle = register(EntropyRandom())
        protect(erHandle)

        val srHandle = register(SimpleRandom(erHandle))
        protect(srHandle)

        val nrHandle = register(NonceRandom(erHandle))
        protect(nrHandle)
    }

    private val _default by lazy { receive(lookup("SimpleRandom-Stupid")) }
    public val default: RandomProxy
        get() = _default

    override fun wrapInProxy(item: AbstractBufferedRandom): RandomProxy = RandomProxy(item)
}