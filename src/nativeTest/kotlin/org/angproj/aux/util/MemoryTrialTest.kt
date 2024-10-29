package org.angproj.aux.util

import org.angproj.aux.io.DataSize
import org.angproj.aux.mem.MemoryFree
import kotlin.test.Test
import kotlin.time.measureTime

class MemoryTrialTest {

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testGC() {
        val reps = 10_000
        val timeMem = measureTime {
            repeat(reps) {
                MemoryFree.allocate(DataSize._1M).close()
            }
        }
        println(timeMem)
        val timeUse = measureTime {
            repeat(reps) {
                MemoryFree.allocate(DataSize._1M).use {}
            }
        }
        println(timeUse)
        val timeArr = measureTime {
            repeat(reps) {
                ByteArray(DataSize._1M.size)
            }
        }
        println(timeArr)
    }
}