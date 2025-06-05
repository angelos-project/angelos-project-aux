package org.angproj.aux.util

import org.angproj.aux.io.Memory
import org.angproj.aux.mem.MemoryFree
import kotlin.test.Test
import kotlin.time.measureTime

class Memory2TrialTest {

    @Test
    fun testGC() {
        val timeMem = measureTime {
            repeat(10_000) {
                MemoryFree.allocate(1_000_000).close()
            }
        }
        println(timeMem)
        val timeUse = measureTime {
            repeat(10_000) {
                MemoryFree.allocate(1_000_000)
            }
        }
        println(timeUse)
        val timeArr = measureTime {
            repeat(10_000) {
                ByteArray(1_000_000)
            }
        }
        println(timeArr)
    }
}