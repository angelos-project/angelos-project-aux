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

import org.angproj.aux.buf.TextBuffer
import org.angproj.aux.util.CodePoint

public interface TextWritable {
    public fun write(codePoint: CodePoint): Int

    public fun write(str: String): Int

    public fun write(txt: Text): Int

    public fun write(text: TextBuffer): Int = write(text, 0, text.limit)

    public fun write(text: TextBuffer, offset: Int, length: Int): Int
}