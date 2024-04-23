package org.angproj.aux.util

import org.angproj.aux.io.MutableMemory
import platform.posix.sched_yield
import kotlin.test.Test

class MemoryTrialTest {

    @Test
    fun testGC() {
        repeat(10_000) {
            repeat(1) {
                val mem = MutableMemory(1_000_000)
                mem.setLong(0, 23525345)
                sched_yield()
            }
        }
    }
}