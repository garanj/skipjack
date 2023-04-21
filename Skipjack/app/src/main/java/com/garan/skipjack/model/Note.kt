package com.garan.skipjack.model

import androidx.compose.ui.graphics.Color

enum class Note {
    A {
        override val scaleOffset = 0
        override val color = Color(0x3a, 0x86, 0xff)
    },
    B {
        override val scaleOffset = 2
        override val color = Color(0x83, 0x38, 0xec)
    },
    C {
        override val scaleOffset = -9
        override val color = Color(0xff, 0x00, 0x6e)
    },
    D {
        override val scaleOffset = -7
        override val color = Color(0xfb, 0x56, 0x07)
    },
    E {
        override val scaleOffset = -5
        override val color = Color(0xff, 0xbe, 0x0b)
    },
    F {
        override val scaleOffset = -4
        override val color = Color(0x8a, 0xc9, 0x26)
    },
    G {
        override val scaleOffset = -2
        override val color = Color(0xf7, 0x25, 0x85)
    };

    abstract val scaleOffset: Int
    abstract val color: Color
}