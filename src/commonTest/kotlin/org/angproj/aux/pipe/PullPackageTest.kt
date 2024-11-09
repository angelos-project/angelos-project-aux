package org.angproj.aux.pipe

import org.angproj.aux.buf.asBinary
import org.angproj.aux.io.DataSize
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.TestObject
import org.angproj.aux.pkg.TestStruct
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * PullPackage uses an underlying binary PullPipe, it has the same logic.
 * */
class PullPackageTest {

    /**
     * The goal is to pull all data from the package source.
     * */
    @Test
    fun testStreamPull() {
        val to1 = TestObject(uuid4())
        val data = BufMgr.binary(DataSize._16K.size)
        ObjectType(to1).enfoldStream(data)

        val r1 = Pipe.buildPackagePullPipe(BlobReader(data.asBinary()))
        val to2 = r1.readObject { TestObject() }

        assertEquals(to1, to2)

        data.clear()
        val ts1 = TestStruct.randomize()
        StructType(ts1).enfoldStream(data)

        val r2 = Pipe.buildPackagePullPipe(BlobReader(data.asBinary()))
        val ts2 = r2.readStruct { TestStruct() }

        r1.close()
        r2.close()
        assertEquals(ts1, ts2)
    }
}