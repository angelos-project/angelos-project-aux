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
package org.angproj.aux.io

import org.angproj.aux.buf.AbstractSpeedCopy

internal actual inline fun <reified T : AbstractSpeedCopy> T.longCopy(
    basePtr: Long,
    copyPtr: Long,
    offset: Int
) {
    throw UnsupportedOperationException()
}

internal actual inline fun <reified T : AbstractSpeedCopy> T.innerMemCopyOfRange(
    idxFrom: Int,
    idxTo: Int
): T {
    throw UnsupportedOperationException()
}