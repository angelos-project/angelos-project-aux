package org.angproj.aux.rand

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.DataBuffer
import java.io.File

class ExportTest {

    //@Test
    fun testExportSmallRandom() {
        /*val data = BinaryBuffer()
        val random = TestSmallRandom()
        generateGigaByte("small_random.bin", 32) {
            data.also { random.read(it) }
        }*/
    }

    //@Test
    fun testExportSponge256() {
        /*val data = DataBuffer()
        val random = TestRandom256(LongArray(4) { SecureRandom.readLong() })
        generateGigaByte("sponge_256.bin", 32) {
            data.asByteArray().also { random.read(it) }
        }*/
    }

    //@Test
    fun testExportSponge512() {
        /*val data = DataBuffer()
        val random = TestRandom512(LongArray(8) { SecureRandom.readLong() })
        generateGigaByte("sponge_512.bin", 32) {
            data.asByteArray().also { random.read(it) }
        }*/
    }

    //@Test
    fun testExportSponge1024() {
        /*val data = DataBuffer()
        val random = TestRandom1024(LongArray(16) { SecureRandom.readLong() })
        generateGigaByte("sponge_1024.bin", 32) {
            data.asByteArray().also { random.read(it) }
        }*/
    }

    private fun generateGigaByte(name: String, gigs: Long, block: () -> ByteArray) {
        /*val targetFile = File(name)
        val output = targetFile.outputStream()
        val times = gigs * 1024L * 1024L * 1024L / block().size.toLong() + 1
        repeat(times.toInt()) {
            output.write(block())
        }
        output.close()*/
    }
}