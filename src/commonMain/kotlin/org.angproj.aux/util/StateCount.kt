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
package org.angproj.aux.util

import kotlin.time.ComparableTimeMark

/**
 * [StateCount] gives the telemetry of a digital function and can be compared with each other of the same type.
 * */
public interface StateCount {
    /**
     * The comparable TimeMark of the very [StateCount] measurement.
     * */
    public val dateTimeMark: ComparableTimeMark

    /**
     * A one line human-readable description.
     * */
    public override fun toString(): String
}