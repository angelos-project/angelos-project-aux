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

import org.angproj.aux.util.Once

@OptIn(ExperimentalStdlibApi::class)
public abstract class AbstractPipePoint<T: PipeType>: AutoCloseable {
    protected var pipe: AbstractPipe<*> by Once()

    public val isPiped: Boolean
        get() = try {
            pipe
            true
        } catch (_: IllegalArgumentException) {
            false
        }

    private var _closed = false
    public val isClosed: Boolean
        get() = _closed

    override fun close(): Unit = when(_closed) {
        false -> {
            dispose()
            _closed = true
            pipe.close()
        }
        else -> Unit
    }

    internal fun connect(pipe: AbstractPipe<*>) {
        this.pipe = pipe
    }

    protected abstract fun dispose()
}