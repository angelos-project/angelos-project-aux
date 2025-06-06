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

import org.angproj.aux.util.Finalizer.stack

public object Finalizer {

    init {
        finalizeSetup()
    }

    private val stack = ArrayDeque<() -> Unit>()

    public fun registerFinalizeHook(action: () -> Unit): Unit = stack.addFirst(action)

    internal fun dispose() {
        while (stack.isNotEmpty()) {
            val cleanUp = stack.removeFirst()
            cleanUp()
        }
    }
}

