package org.angproj.aux.mem

import org.angproj.aux.io.Bytes
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual object Default : MemoryManager {
    actual override fun allocate(dataSize: DataSize): Segment = Bytes(dataSize.size)

    actual override fun recycle(segment: Segment) { segment.close() }
}