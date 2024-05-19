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
package org.angproj.aux.pipe

import org.angproj.aux.buf.Pump
import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.PumpReader

public class BinarySource(
    pump: PumpReader = Pump
): AbstractSource<BinaryType>(pump), BinaryType, BinaryReadable {
    override fun dispose() {
        TODO("Not yet implemented")
    }

    override fun readByte(): Byte {
        TODO("Not yet implemented")
    }

    override fun readUByte(): UByte {
        TODO("Not yet implemented")
    }

    override fun readShort(): Short {
        TODO("Not yet implemented")
    }

    override fun readUShort(): UShort {
        TODO("Not yet implemented")
    }

    override fun readInt(): Int {
        TODO("Not yet implemented")
    }

    override fun readUInt(): UInt {
        TODO("Not yet implemented")
    }

    override fun readLong(): Long {
        TODO("Not yet implemented")
    }

    override fun readULong(): ULong {
        TODO("Not yet implemented")
    }

    override fun readFloat(): Float {
        TODO("Not yet implemented")
    }

    override fun readDouble(): Double {
        TODO("Not yet implemented")
    }
}