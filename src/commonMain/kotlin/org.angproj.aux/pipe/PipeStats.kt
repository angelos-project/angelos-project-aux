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

import org.angproj.aux.util.StateCount
import kotlin.time.ComparableTimeMark

/**
 * [dateTimeMark] is the current comparable timestamp when the stats were measured.
 * [pumpOpen] the current availability of the pump side of the pipe based on automatic closing.
 * [pipeOpen] is the direct availability of the pipe itself depending on manual closing.
 * [staleCnt] the number of stale access attempts of the pump in a row, automatically closes on 3 but resets on one successful.
 * [pumpCnt] the number of bytes passed through the pump.
 * [pipeCnt] the number of bytes passed directly through the pipe.
 * [usedMem] the current actual number of bytes buffered inside the pipe.
 * [allocMem] the current total number of allocated bytes inside the buffer of the pipe.
 * [curSegCnt] the current number of segments in the buffer of the pipe.
 * [totSegCnt] the total number of segments poured into/from the inside of the pipe including the buffer.
 * */
public data class PipeStats(
    override val dateTimeMark: ComparableTimeMark,
    val pumpOpen: Boolean,
    val pipeOpen: Boolean,
    val staleCnt: Int,
    val pumpCnt: Long,
    val pipeCnt: Long,
    val usedMem: Int,
    val allocMem: Int,
    val curSegCnt: Int,
    val totSegCnt: Long,
) : StateCount {

}