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

    override val sizeMode: SizeMode = SizeMode.FIXED
    override val dataSize: DataSize = DataSize._4K

    override fun decode(data: ByteArray): IntArray {
        TODO("Not yet implemented")
    }

}