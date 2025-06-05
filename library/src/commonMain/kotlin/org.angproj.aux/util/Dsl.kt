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


public interface DslBlock

public operator fun <E : DslBlock> E.invoke(block: E.() -> Unit): E = this.also { block() }

public inline operator fun <reified E : DslBlock, R: Any> E.invoke(block: E.() -> R): R = this.block()


public fun interface Lambda<E> {
    public operator fun invoke(): E
}