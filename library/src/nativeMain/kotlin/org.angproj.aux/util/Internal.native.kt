/**
 * Copyright (c) 2021-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.staticCFunction
import platform.posix.atexit


internal fun finalizeDispose() { Finalizer.dispose() }

@OptIn(ExperimentalForeignApi::class)
internal actual fun finalizeSetup() {
    atexit(staticCFunction(::finalizeDispose))
}