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
package org.angproj.aux.buf

import org.angproj.aux.buf.PipeMode.*
import org.angproj.aux.util.Once
import org.angproj.aux.util.isNull

public abstract class AbstractSource<T: PipeType>(
    protected val pump: Pump = Pump
): AbstractPipePoint(), Source<T> {
}