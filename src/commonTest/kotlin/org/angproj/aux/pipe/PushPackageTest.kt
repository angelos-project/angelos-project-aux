package org.angproj.aux.pipe

import org.angproj.aux.buf.asBinary
import org.angproj.aux.io.DataSize
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.TestObject
import org.angproj.aux.pkg.TestStruct
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.util.uuid4
import kotlin.test.*

class PushPackageTest {
    /**
     * The goal is to pull all data from the TextSource.
     * */
    @Test
    fun testStreamPush() {
        val data = BufMgr.binary(DataSize._16K.size)
        val w1 = Pipe.buildPackagePushPipe(BlobWriter(data.asBinary()))

        val to1 = TestObject(uuid4())
        val l1 = w1.writeObject(to1)
        w1.flush()
        data.limitAt(l1.toInt())

        val to2 = ObjectType.unfoldFromStream(data) { TestObject() }.value
        assertEquals(to1, to2)

        data.clear()
        val w2 = Pipe.buildPackagePushPipe(BlobWriter(data.asBinary()))

        val ts1 = TestStruct.randomize()
        val l2 = w2.writeStruct(ts1)
        w2.flush()
        data.limitAt(l2.toInt())

        val ts2 = StructType.unfoldFromStream(data) { TestStruct() }.value
        assertEquals(ts1, ts2)
    }
}