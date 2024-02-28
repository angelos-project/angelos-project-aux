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
package org.angproj.aux.pkg

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Retrievable

public interface UnfoldableCollection<E: EnfoldableCollection>: Unfoldable<EnfoldableCollection> {

    public fun unfold(inData: Retrievable, offset: Int, size: Int) : E

    public fun unfold(inStream: Readable) : E
}