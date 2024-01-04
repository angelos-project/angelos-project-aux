package org.angproj.aux.sm

import kotlin.test.*

class StateMachineTest {

    @Test
    fun getState() {
        val esm = ExampleStateMachine.create()
        esm.state = ExampleStateMachine.RUNNING
        esm.state = ExampleStateMachine.FINNISH
        assertTrue { esm.done }
    }

    @Test
    fun setState() {
        val esm = ExampleStateMachine.create()
        esm.state = ExampleStateMachine.RUNNING
        assertEquals(esm.state, ExampleStateMachine.RUNNING)
    }

    @Test
    fun getDone() {
        val esm = ExampleStateMachine.create()
        assertFalse(esm.done)
    }

    @Test
    fun getTransitionPaths() {
        val esm = ExampleStateMachine.create()
        assertTrue(esm.transitionPaths.isNotEmpty())
    }
}