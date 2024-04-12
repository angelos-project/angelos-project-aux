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

public interface ExcHelper<T: Throwable> {

    /**
     * Assert that entered arguments are valid.
     * */
    public fun req(value: Boolean, lazyMsg: () -> String = { "" }): Unit {
        if(!value) throw thrower(lazyMsg())
    }

    /**
     * Predetermine that a certain status has been reached.
     * */
    public fun chk(value: Boolean, lazyMsg: () -> String = { "" }): Unit {
        if(!value) throw thrower(lazyMsg())
    }

    /**
     * Call when a mistaken error happens.
     * */
    public fun err(lazyMsg: () -> String): Nothing {
        err(lazyMsg())
    }

    public fun err(lazyMsg: String): Nothing {
        throw thrower(lazyMsg)
    }

    public fun thrower(msg: String): T
}