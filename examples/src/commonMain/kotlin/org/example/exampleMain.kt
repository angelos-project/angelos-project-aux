package org.example

import org.angproj.aux.io.asBinary
import org.angproj.aux.io.toByteArray
import org.angproj.aux.io.toText


public fun main() {
    val text = "Hello, World!".toText().asBinary()
    println(text.toByteArray().decodeToString())
}