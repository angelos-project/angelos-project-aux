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
package org.angproj.aux.math

import kotlin.math.cos
import kotlin.math.sin

public data class Complex(val r: Double, val i: Double) {
    public override fun toString(): String = "($r, $i)"

    public companion object
}

public fun Complex.Companion.exp(a: Double): Complex = Complex(cos(a), sin(a))

public operator fun Complex.plus(c: Complex): Complex = Complex(r + c.r, i + c.i)
public operator fun Complex.minus(c: Complex): Complex = Complex(r - c.r, i - c.i)
public operator fun Complex.times(c: Complex): Complex = Complex(r*c.r - i*c.i, r*c.i + i*c.r)
public operator fun Complex.times(a: Double): Complex = Complex(a*r, a*i)
public operator fun Complex.div(a: Double): Complex = Complex(r/a, i/a)