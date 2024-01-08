package org.angproj.aux.sm

import org.angproj.aux.fsm.States

enum class ExampleStateMachine {
    START, RUNNING, FINNISH;

    companion object: org.angproj.aux.fsm.States<ExampleStateMachine> {
        override val transitionMap = mapOf(
            START to listOf(RUNNING),
            RUNNING to listOf(RUNNING, FINNISH),
            FINNISH to listOf()
        )

        override val beginsWith = START
        override val endsWith = FINNISH
    }
}