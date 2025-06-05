package org.angproj.aux.io

import kotlin.test.*


abstract class MemBlockTest<E: MemBlock> {

    protected abstract val txtLen: Int

    abstract fun setInput(): E

    @Test
    fun testEquals() {
        val diff = setInput()
        diff._segment.setByte(txtLen-1, 0x21)
        assertNotEquals(diff, setInput())
        assertEquals(setInput(), setInput())
    }

    @Test
    fun getCapacity() { assertEquals(setInput().capacity, txtLen) }

    @Test
    fun getLimit() { assertEquals(setInput().limit, txtLen) }

    @Test
    fun limitAt() {
        val buf = setInput()

        assertFailsWith<IllegalArgumentException> { buf.limitAt(-1) }
        assertFailsWith<IllegalArgumentException> { buf.limitAt(buf.limit+1) }

        buf.limitAt(txtLen - 5)
        assertEquals(buf.limit, txtLen - 5)
    }

    @Test
    fun clear() {
        val buf = setInput()
        assertEquals(buf.limit, buf.capacity)

        buf.limitAt(txtLen - 5)
        assertEquals(buf.limit, txtLen - 5)

        buf.clear()
        assertEquals(buf.limit, txtLen)
    }

    @Test
    fun asBinary() { assertIs<Binary>(setInput().asBinary()) }

    @Test
    fun isView() {
        assertEquals(setInput().isView(), false)
    }

    @Test
    fun isMem() {
        assertEquals(setInput().isMem(), false)
    }

    @Test
    fun close() {
        setInput().close()
    }
}