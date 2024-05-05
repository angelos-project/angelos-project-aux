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
package org.angproj.aux.fsm

public class StateMachine<E>(
    public val transitionPaths: Map<E, List<E>>,
    private var _current: E,
    private val end: E,
    config: StateMachine<E>.() -> Unit
) {

    init {
        require(transitionPaths.containsKey(_current))
        require(transitionPaths.containsKey(end))
        require(transitionPaths[end]!!.isEmpty())

        config()
    }

    public val current: E
        get() = _current

    public var state: E
        get() = _current
        set(value) {
            require(transitionPaths.containsKey(_current)) { "Valid state '$value' not configured in state paths." }
            check(transitionPaths[_current]!!.contains(value)) {
                "Attempted invalid transition from state '$_current' to '$value'."
            }
            _current = value
        }

    public val done: Boolean
        get() = _current == end
}