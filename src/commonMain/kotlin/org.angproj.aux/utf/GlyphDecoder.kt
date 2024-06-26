package org.angproj.aux.utf

import org.angproj.aux.codec.Decoder
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.SizeMode
import org.angproj.aux.io.Sizeable

/**
 * UTF-8 Decoder
 * Decodes byte sequences to code points.
 * */
public class GlyphDecoder : Decoder<ByteArray, IntArray>, Sizeable {

    override val sizeMode: SizeMode = SizeMode.MAXIMUM
    override val dataSize: DataSize = DataSize._1K


    private val inQueue: ArrayDeque<ByteArray> = ArrayDeque()
    private val outQueue: ArrayDeque<IntArray> = ArrayDeque()

    override fun decode(data: ByteArray): IntArray {
        inQueue.addLast(data)
        return outQueue.removeFirst()
    }

}