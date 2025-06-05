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
package org.angproj.aux.codec

import org.angproj.aux.buf.wrap
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.utf.Ascii
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.floorMod

public class Base64Encoder : Encoder<Binary, Text> {
    private var output: Segment<*> = NullObject.segment

    override val inputCount: Long
        get() = TODO("Not yet implemented")
    override val inputStale: Boolean
        get() = TODO("Not yet implemented")

    private inline fun <reified E : Any> convertLE(value: Int): Int {
        return ((bin2base[value and 0x3F] and 0xFF) shl 24) or
                ((bin2base[(value ushr 6) and 0x3F] and 0xFF) shl 16) or
                ((bin2base[(value ushr 12) and 0x3F] and 0xFF) shl 8) or
                (bin2base[(value ushr 18) and 0x3F] and 0xFF)
    }

    private inline fun <reified E : Any> convertBE(value: Int, first: Boolean = false): Int {
        val data = if (first) value shl 8 else value and 0xFFFFFF
        return (bin2base[data and 0x3F] and 0xFF) or
                ((bin2base[(data ushr 6) and 0x3F] and 0xFF) shl 8) or
                ((bin2base[(data ushr 12) and 0x3F] and 0xFF) shl 16) or
                ((bin2base[(data ushr 18) and 0x3F] and 0xFF) shl 24)
    }

    private inline fun <reified E : Any> proportion(len: Int): Int = (
            len.floorDiv(3) + (if (len.floorMod(3) > 0) 1 else 0)) * 4

    override fun encode(data: Binary): Text {
        val size = proportion<Unit>(data.limit)
        val text = BufMgr.txt(size)
        output = text._segment
        if (size > 0) write(data._segment)
        return text
    }

    /**
     * This [write] function is naive it generally expects the incoming segment to be divisible by thee
     * or assumes it is the last segment of the code to be converted into Base64 encoding. It also
     * expects there to be another segment equally divisible according to proportion 3:4.
     * */
    override fun write(data: Segment<*>): Int {
        require(output.limit == proportion<Unit>(data.limit))
        BufMgr.asWrap(output) {
            this.wrap {
                val remainder = data.limit.floorMod(3)
                val len = data.limit - remainder - if(remainder == 0) 3 else 0
                var idx = 0
                while(idx < len) {
                    writeInt(convertLE<Unit>(data.getInt(idx).asNet() ushr 8))
                    idx += 3
                }
                when (data.limit - idx) {
                    1 -> ((convertLE<Unit>(
                        data.getByte(data.limit - 1).toInt() shl 16
                    ) and 0xFFFF) or ((Ascii.PRNT_EQUALS.cp shl 24) or (Ascii.PRNT_EQUALS.cp shl 16)))
                    2 -> ((convertLE<Unit>(
                        (data.getByte(data.limit - 2).toInt() shl 16) or (
                                data.getByte(data.limit - 1).toInt() shl 8)
                    ) and 0xFFFFFF)) or (Ascii.PRNT_EQUALS.cp shl 24)
                    3 -> convertLE<Unit>(
                        (data.getByte(data.limit - 3).toInt() shl 16) or
                                (data.getByte(data.limit - 2).toInt() shl 8) or
                                (data.getByte(data.limit - 1).toInt())
                    )
                    else -> error("Undefined behavior")
                }.also { writeInt(it) }
            }
        }
        return output.limit
    }

    private companion object {
        private val bin2base: IntArray = intArrayOf(
            Ascii.PRNT_A_UP.cp, Ascii.PRNT_B_UP.cp,
            Ascii.PRNT_C_UP.cp, Ascii.PRNT_D_UP.cp, Ascii.PRNT_E_UP.cp, Ascii.PRNT_F_UP.cp,
            Ascii.PRNT_G_UP.cp, Ascii.PRNT_H_UP.cp, Ascii.PRNT_I_UP.cp, Ascii.PRNT_J_UP.cp,
            Ascii.PRNT_K_UP.cp, Ascii.PRNT_L_UP.cp, Ascii.PRNT_M_UP.cp, Ascii.PRNT_N_UP.cp,
            Ascii.PRNT_O_UP.cp, Ascii.PRNT_P_UP.cp, Ascii.PRNT_Q_UP.cp, Ascii.PRNT_R_UP.cp,
            Ascii.PRNT_S_UP.cp, Ascii.PRNT_T_UP.cp, Ascii.PRNT_U_UP.cp, Ascii.PRNT_V_UP.cp,
            Ascii.PRNT_W_UP.cp, Ascii.PRNT_X_UP.cp, Ascii.PRNT_Y_UP.cp, Ascii.PRNT_Z_UP.cp,

            Ascii.PRNT_A_LOW.cp, Ascii.PRNT_B_LOW.cp,
            Ascii.PRNT_C_LOW.cp, Ascii.PRNT_D_LOW.cp, Ascii.PRNT_E_LOW.cp, Ascii.PRNT_F_LOW.cp,
            Ascii.PRNT_G_LOW.cp, Ascii.PRNT_H_LOW.cp, Ascii.PRNT_I_LOW.cp, Ascii.PRNT_J_LOW.cp,
            Ascii.PRNT_K_LOW.cp, Ascii.PRNT_L_LOW.cp, Ascii.PRNT_M_LOW.cp, Ascii.PRNT_N_LOW.cp,
            Ascii.PRNT_O_LOW.cp, Ascii.PRNT_P_LOW.cp, Ascii.PRNT_Q_LOW.cp, Ascii.PRNT_R_LOW.cp,
            Ascii.PRNT_S_LOW.cp, Ascii.PRNT_T_LOW.cp, Ascii.PRNT_U_LOW.cp, Ascii.PRNT_V_LOW.cp,
            Ascii.PRNT_W_LOW.cp, Ascii.PRNT_X_LOW.cp, Ascii.PRNT_Y_LOW.cp, Ascii.PRNT_Z_LOW.cp,

            Ascii.PRNT_ZERO.cp, Ascii.PRNT_ONE.cp, Ascii.PRNT_TWO.cp, Ascii.PRNT_THREE.cp,
            Ascii.PRNT_FOUR.cp, Ascii.PRNT_FIVE.cp, Ascii.PRNT_SIX.cp, Ascii.PRNT_SEVEN.cp,
            Ascii.PRNT_EIGHT.cp, Ascii.PRNT_NINE.cp, Ascii.PRNT_PLUS.cp, Ascii.PRNT_SLASH.cp,
        )
    }
}