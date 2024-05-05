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
package org.angproj.aux.buf

import org.angproj.aux.fsm.StateMachine
import org.angproj.aux.fsm.States
import org.angproj.aux.util.Once

public object PipeBuilder {
    public fun build(): Context = Context()

    internal enum class Builder {
        START,
        SOURCE,
        PIPE,
        SINK,
        FINISH,
        ERROR;

        companion object: States<Builder> {
            override val transitionMap: Map<Builder, List<Builder>> = mapOf(
                START to listOf(SOURCE),
                SOURCE to listOf(PIPE),
                PIPE to listOf(SINK),
                SINK to listOf(FINISH),
                FINISH to listOf(),
                ERROR to listOf()
            )

            override val beginsWith: Builder = START
            override val endsWith: Builder = FINISH
        }
    }

    public class Context {
        private val fsm: StateMachine<Builder> = Builder.create()

        private var src: Source<*> by Once()
        private var pipe: Pipe<*, *> by Once()
        private var sink: Sink<*> by Once()

        init {
            fsm.state = Builder.SOURCE
        }
    }
}