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

public interface States<E: Enum<E>> {
    public val transitionMap: Map<E, List<E>>
    public val beginsWith: E
    public val endsWith: E

    public fun create(
        config: StateMachine<E>.() -> Unit = {}
    ): StateMachine<E> = StateMachine(transitionMap, beginsWith, endsWith, config)
}