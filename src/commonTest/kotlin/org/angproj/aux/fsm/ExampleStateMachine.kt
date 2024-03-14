package org.angproj.aux.fsm

enum class ExampleStateMachine {
    START, RUNNING, FINNISH;

    companion object: States<ExampleStateMachine> {
        override val transitionMap = mapOf(
            START to listOf(RUNNING),
            RUNNING to listOf(RUNNING, FINNISH),
            FINNISH to listOf()
        )

        override val beginsWith = START
        override val endsWith = FINNISH
    }
}