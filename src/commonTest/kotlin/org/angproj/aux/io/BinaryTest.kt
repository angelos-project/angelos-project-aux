package org.angproj.aux.io

import kotlin.test.Test
import kotlin.test.assertEquals


class BinaryTest {

    @Test
    fun getSegment() {
        (1000 until 1024).forEach {
            val bin = Binary(it)
            assertEquals(bin.limit, it)
        }
    }

    fun getLimit() {
    }

    fun getCapacity() {
    }

    fun retrieveByte() {
    }

    fun retrieveUByte() {
    }

    fun retrieveShort() {
    }

    fun retrieveUShort() {
    }

    fun retrieveInt() {
    }

    fun retrieveUInt() {
    }

    fun retrieveLong() {
    }

    fun retrieveULong() {
    }

    fun retrieveFloat() {
    }

    fun retrieveDouble() {
    }

    fun storeByte() {
    }

    fun storeUByte() {
    }

    fun storeShort() {
    }

    fun storeUShort() {
    }

    fun storeInt() {
    }

    fun storeUInt() {
    }

    fun storeLong() {
    }

    fun storeULong() {
    }

    fun storeFloat() {
    }

    fun storeDouble() {
    }

    fun isView() {
    }

    fun isMem() {
    }

    fun close() {
    }

    fun asBinary() {
    }

    fun get_segment() {
    }

    fun getView() {
    }
}