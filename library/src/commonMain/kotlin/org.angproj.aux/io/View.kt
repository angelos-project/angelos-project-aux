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

/**
 * The View interface has two properties:
 *  The first one, isView, says whether the view is a wrapping of a shared Segment, thereby not allowed to close it.
 *  The second, isMem, says whether the wrapped Segment is raw memory access or not. In such case it must be closed
 *  if it is original.
 * */
public interface View {
    public fun isView(): Boolean

    public fun isMem(): Boolean
}