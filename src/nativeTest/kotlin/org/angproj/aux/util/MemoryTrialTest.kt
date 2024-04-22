package org.angproj.aux.util

import kotlinx.cinterop.*
import org.angproj.aux.Blob
import org.angproj.aux.Chunk
import org.angproj.aux.io.MutableMemory
import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalStdlibApi::class, ExperimentalForeignApi::class)
class Chunk2(size: Int): AutoCloseable {

    val mem: CPointer<ByteVarOf<Byte>>
    val ptr: Long

    init {
        mem = nativeHeap.allocArray<ByteVar>(size)
        ptr = mem.pointed.ptr.toLong()
    }

    override fun close() {
        nativeHeap.free(mem)
    }

}

class MemoryTrialTest {

    @Test
    fun testGC() {
        repeat(10_000) {
            repeat(1) {
                val mem = Chunk2(1_000_000)
            }
        }
    }
}