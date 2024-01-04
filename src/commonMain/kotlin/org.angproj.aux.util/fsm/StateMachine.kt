/**
 * Copyright (c) 2023-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.util.fsm

public class StateMachine<E>(
    public val transitionPaths: Map<E, List<E>>,
    private var current: E,
    private val end: E,
    config: StateMachine<E>.() -> Unit
) {

    init {
        require(transitionPaths.containsKey(current))
        require(transitionPaths.containsKey(end))
        require(transitionPaths[end]!!.isEmpty())

        config()
    }

    public var state: E
        get() = current
        set(value) {
            require(transitionPaths.containsKey(current)) { "Valid state '$value' not configured in state paths." }
            check(transitionPaths[current]!!.contains(value)) {
                "Attempted invalid transition from state '$current' to '$value'." }
            current = value
        }

    public val done: Boolean
        get() = current == end
}