package org.angproj.aux.util

import org.angproj.aux.utf.Ascii

public object Hex {
    public val valid: Set<Int> = setOf(
        Ascii.PRNT_ZERO.cp,
        Ascii.PRNT_ONE.cp,
        Ascii.PRNT_TWO.cp,
        Ascii.PRNT_THREE.cp,
        Ascii.PRNT_FOUR.cp,
        Ascii.PRNT_FIVE.cp,
        Ascii.PRNT_SIX.cp,
        Ascii.PRNT_SEVEN.cp,
        Ascii.PRNT_EIGHT.cp,
        Ascii.PRNT_NINE.cp,
        Ascii.PRNT_A_LOW.cp,
        Ascii.PRNT_B_LOW.cp,
        Ascii.PRNT_C_LOW.cp,
        Ascii.PRNT_D_LOW.cp,
        Ascii.PRNT_E_LOW.cp,
        Ascii.PRNT_F_LOW.cp,
        Ascii.PRNT_A_UP.cp,
        Ascii.PRNT_B_UP.cp,
        Ascii.PRNT_C_UP.cp,
        Ascii.PRNT_D_UP.cp,
        Ascii.PRNT_E_UP.cp,
        Ascii.PRNT_F_UP.cp
    )

    public val hex2bin: Map<Int, Int> = mapOf(
        Ascii.PRNT_ZERO.cp to 0x0,
        Ascii.PRNT_ONE.cp to 0x1,
        Ascii.PRNT_TWO.cp to 0x2,
        Ascii.PRNT_THREE.cp to 0x3,
        Ascii.PRNT_FOUR.cp to 0x4,
        Ascii.PRNT_FIVE.cp to 0x5,
        Ascii.PRNT_SIX.cp to 0x6,
        Ascii.PRNT_SEVEN.cp to 0x7,
        Ascii.PRNT_EIGHT.cp to 0x8,
        Ascii.PRNT_NINE.cp to 0x9,
        Ascii.PRNT_A_LOW.cp to 0xA,
        Ascii.PRNT_B_LOW.cp to 0xB,
        Ascii.PRNT_C_LOW.cp to 0xC,
        Ascii.PRNT_D_LOW.cp to 0xD,
        Ascii.PRNT_E_LOW.cp to 0xE,
        Ascii.PRNT_F_LOW.cp to 0xF,
        Ascii.PRNT_A_UP.cp to 0xA,
        Ascii.PRNT_B_UP.cp to 0xB,
        Ascii.PRNT_C_UP.cp to 0xC,
        Ascii.PRNT_D_UP.cp to 0xD,
        Ascii.PRNT_E_UP.cp to 0xE,
        Ascii.PRNT_F_UP.cp to 0xF
    )

    public val bin2hex: List<Int> = listOf(
        Ascii.PRNT_ZERO.cp,
        Ascii.PRNT_ONE.cp,
        Ascii.PRNT_TWO.cp,
        Ascii.PRNT_THREE.cp,
        Ascii.PRNT_FOUR.cp,
        Ascii.PRNT_FIVE.cp,
        Ascii.PRNT_SIX.cp,
        Ascii.PRNT_SEVEN.cp,
        Ascii.PRNT_EIGHT.cp,
        Ascii.PRNT_NINE.cp,
        Ascii.PRNT_A_LOW.cp,
        Ascii.PRNT_B_LOW.cp,
        Ascii.PRNT_C_LOW.cp,
        Ascii.PRNT_D_LOW.cp,
        Ascii.PRNT_E_LOW.cp,
        Ascii.PRNT_F_LOW.cp
    )

    internal inline fun<reified R: Number> Byte.upperToHex(): Int = bin2hex[toInt() ushr 4 and 0xf]

    internal inline fun<reified R: Number> Byte.lowerToHex(): Int = bin2hex[toInt() and 0xf]
}