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

import org.angproj.aux.io.View

public interface Auto: View, Closeable {
}

public inline fun<reified T: Auto, R> T.useWith(block: (T) -> R
): R {
   return try {
        block(this)
    } finally {
        if(!isView() && isMem()) close()
    }
}