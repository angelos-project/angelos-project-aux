package org.angproj.aux.num

import kotlin.test.*
import java.math.BigInteger as JavaBigInteger

class MutableBigIntTest {

    fun mutableBigIntOf(value: ByteArray): MutableBigInt {
        return BigMath.fromByteArray(value) { m, s -> MutableBigInt(m.toMutableList(), s ) }
    }

    /**
     * This test recognizes that BigInt and Java BigInteger interprets a ByteArray of some random values
     * the same when importing from the said ByteArray and exporting to a new ByteArray.
     * */
    @Test
    fun testByteArray() {
        Combinator.numberGenerator(-64..64) { x ->
            val xBi2 = mutableBigIntOf(x)
            val xJbi = JavaBigInteger(x)

            assertContentEquals(xBi2.toByteArray(), xJbi.toByteArray())
        }
    }

    /**
     * This test recognizes that BigInt and Java BigInteger calculates
     * the sigNum of the same underlying value similarly.
     * */
    @Test
    fun testSigNum() {
        Combinator.numberGenerator(-64..64) { x ->
            val xBi2 = mutableBigIntOf(x)
            val xJbi = JavaBigInteger(x)

            assertEquals(xBi2.sigNum.state, xJbi.signum())
        }
    }

    /**
     * This test recognizes that BigInt and Java BigInteger calculates
     * the bitLength of the same underlying value similarly.
     * */
    @Test
    fun testBitLength() {
        Combinator.numberGenerator(-64..64) { x ->
            val xBi2 = mutableBigIntOf(x)
            val xJbi = JavaBigInteger(x)

            assertEquals(xBi2.bitLength, xJbi.bitLength())
        }
    }

    /**
     * This test recognizes that BigInt and Java BigInteger calculates
     * the bitCount of the same underlying value similarly.
     * */
    @Test
    fun testBitCount() {
        Combinator.numberGenerator(-64..64) { x ->
            val xBi2 = mutableBigIntOf(x)
            val xJbi = JavaBigInteger(x)

            assertEquals(xBi2.bitCount, xJbi.bitCount())
        }
    }
}