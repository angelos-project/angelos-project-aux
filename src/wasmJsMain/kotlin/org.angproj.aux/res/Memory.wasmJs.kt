package org.angproj.aux.res

import org.angproj.aux.util.Copyable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Memory(public actual val size: Int, public actual val ptr: Long) : Copyable, Cleanable {
    public actual override fun dispose() { TODO() }

    actual override val limit: Int = size

    actual override fun getLong(index: Int): Long = TODO()

    actual override fun getByte(index: Int): Byte = TODO()

    actual override fun setLong(index: Int, value: Long): Unit = TODO()

    actual override fun setByte(index: Int, value: Byte): Unit = TODO()
}

public actual fun allocateMemory(size: Int): Memory {
    TODO("Not yet implemented")
}

@PublishedApi
internal actual fun speedMemCpy(
    idxFrom: Int,
    idxTo: Int,
    dstOff: Int,
    src: Long,
    dst: Long
): Int {
    TODO("Not yet implemented")
}