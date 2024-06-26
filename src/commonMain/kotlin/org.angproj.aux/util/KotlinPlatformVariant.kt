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

public enum class KotlinPlatformVariant(public val code: String) {
    COMMON("Kotlin/Common"),
    NATIVE("Kotlin/Native"),
    JVM("Kotlin/Jvm"),
    JS("Kotlin/Js"),
    WASM("Kotlin/Wasm");

    public companion object
}

public expect fun KotlinPlatformVariant.Companion.getVariant(): KotlinPlatformVariant

public inline fun<reified E: Any> ifNative(block: () -> E): E = when(KotlinPlatformVariant.getVariant()) {
    KotlinPlatformVariant.NATIVE -> block()
    else -> Unit as E
}

public inline fun<reified E: Any> ifJvm(block: () -> E): E = when(KotlinPlatformVariant.getVariant()) {
    KotlinPlatformVariant.JVM -> block()
    else -> Unit as E
}

public inline fun<reified E: Any> ifJs(block: () -> E): E = when(KotlinPlatformVariant.getVariant()) {
    KotlinPlatformVariant.JS -> block()
    else -> Unit as E
}

public inline fun<reified E: Any> ifNotJs(block: () -> E): E = when(KotlinPlatformVariant.getVariant()) {
    KotlinPlatformVariant.JS -> Unit as E
    else -> block()
}