/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.aux.buf

import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.chunkLoop
import kotlin.math.min
import kotlin.test.Test

val theVeryFunData = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer auctor nisi eu bibendum sodales. Integer dui nulla, 
gravida sit amet laoreet in, ultricies quis risus. Praesent iaculis fermentum risus non placerat. Phasellus dictum 
quis velit sed fermentum. Vestibulum bibendum ex vitae dolor mollis, vitae tincidunt orci porta. Donec elementum nisl 
semper, euismod elit nec, pharetra neque. Vestibulum luctus in diam sed mattis. Class aptent taciti sociosqu ad 
litora torquent per conubia nostra, per inceptos himenaeos. Nullam convallis condimentum massa, nec condimentum 
tortor. Nam vel lectus vitae nisi viverra finibus nec quis ante. Sed diam sem, suscipit ac nibh finibus, semper 
volutpat nulla. Ut at convallis elit.

Aliquam tempus erat erat, in commodo massa molestie non. Nullam malesuada molestie orci eget volutpat. Integer 
volutpat sagittis risus quis malesuada. In hac habitasse platea dictumst. Proin lobortis at leo ut suscipit. Integer 
convallis congue nibh id ultrices. Nunc accumsan ut turpis sed tempus. Integer auctor vitae odio sed ullamcorper. 
Donec ac ante libero.

Fusce risus risus, laoreet sit amet ornare vel, dignissim id urna. Vivamus leo nulla, interdum eget semper vitae, 
elementum eget tellus. Mauris sit amet ultrices elit, vel rutrum tellus. Curabitur ac dolor quis arcu tincidunt 
tempus. Sed sed rhoncus metus. Cras velit sapien, luctus vitae vehicula a, condimentum id velit. Proin ac nibh 
consequat, tristique eros id, dignissim lacus. Nullam blandit, mauris in ullamcorper semper, mauris nulla porttitor 
velit, eu sodales lectus lacus non diam. Maecenas eu tellus id odio laoreet facilisis.

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean risus odio, ullamcorper a urna non, sagittis rutrum 
augue. Suspendisse potenti. Proin tristique nisi quam, non lacinia nibh facilisis sit amet. Integer pulvinar, urna 
efficitur dignissim luctus, sapien lectus pellentesque ex, in mattis arcu nisl ac metus. Maecenas vel malesuada erat, 
posuere sagittis tortor. Cras at magna quis libero tempor convallis a sed augue. Aliquam ac nulla sed dui porttitor 
vehicula commodo ac lectus. Vestibulum eget lorem sed lorem sagittis ornare vitae non diam. Donec quis mauris sit 
amet lectus convallis maximus quis a felis. Etiam maximus accumsan erat ut viverra.

Nullam quis finibus ipsum, tincidunt pharetra sem. Fusce pulvinar efficitur eleifend. Proin tincidunt auctor 
dictum. Phasellus hendrerit ante sit amet consectetur suscipit. Donec consequat posuere augue, congue interdum dui 
iaculis vitae. Nullam dignissim mi purus, eu euismod dolor dapibus eget. Pellentesque habitant morbi tristique 
senectus et netus et malesuada fames ac turpis egestas. Mauris auctor fermentum turpis non facilisis. Curabitur ac 
erat sed ex varius suscipit. Duis ut euismod urna. Phasellus elit est, euismod eu dapibus non, fermentum nec purus. 
Maecenas vehicula ligula ac orci sodales fermentum. Suspendisse vel enim in lacus malesuada vulputate lacinia id erat. 
Fusce volutpat hendrerit sapien ut mollis.
""".trimIndent()

/*class BinaryReader(data: ByteArray) : PumpReader {
    val data = DataBuffer(data)

    override val outputCount: Long
        get() = data.position.toLong()

    override val outputStale: Boolean
        get() = data.limit - data.position <= 0

    override fun read(data: Segment<*>): Int {
        data.limitAt(min(data.limit, this.data.remaining))

        var index = chunkLoop<Reify>(0, data.limit, TypeSize.long) {
            data.setLong(it, this.data.readLong())
        }
        index = chunkLoop<Reify>(index, data.limit, TypeSize.byte) {
            data.setByte(it, this.data.readByte())
        }
        return index
    }

}*/

class BinaryPipeTest {
    /*@Test
    fun testBuildTextPipe() {
        val readable = PullPipe(PumpSource<BinaryType>(BinaryReader(theVeryFunData.encodeToByteArray()))).getSink()
        println(readable.readLong())
    }*/

    @Test
    fun testFixTrix() {
        /*TextSource(Pump).isPiped()
        val pullPipe = PullPipe(TextSource(Pump))
        pullPipe.isText()
        pullPipe.isBinary()
        pullPipe.isPackage()
        pullPipe.isPull()
        pullPipe.isPush()
        pullPipe.bufferSize
        val readable = pullPipe.getReadable()
        readable.readGlyph()
        println(readable.readGlyph().toString())*/
    }
}