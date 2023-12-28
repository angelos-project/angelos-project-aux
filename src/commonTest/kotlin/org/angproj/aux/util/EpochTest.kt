package org.angproj.aux.util

import kotlin.test.Test
import kotlin.test.assertTrue

class EpochTest {

    @Test
    fun epochTest() {
        assertTrue(Epoch.getEpochMilliSecs() > 0)
    }
}