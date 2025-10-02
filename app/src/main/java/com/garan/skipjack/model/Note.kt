/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.garan.skipjack.model

import androidx.compose.ui.graphics.Color

enum class Note {
    A {
        override val scaleOffset = 0
        override val color = Color(0xff, 0x00, 0x00)
    },
    A_SHARP {
        override val scaleOffset = 1
        override val color = Color(0xff, 0x55, 0x00)
        override val isSharp = true
    },
    B {
        override val scaleOffset = 2
        override val color = Color(0xff, 0xaa, 0x00)
    },
    C {
        override val scaleOffset = -9
        override val color = Color(0xff, 0xff, 0x00)
    },
    C_SHARP {
        override val scaleOffset = -8
        override val color = Color(0xaa, 0xff, 0x00)
        override val isSharp = true
    },
    D {
        override val scaleOffset = -7
        override val color = Color(0x55, 0xff, 0x00)
    },
    D_SHARP {
        override val scaleOffset = -6
        override val color = Color(0x00, 0xff, 0xaa)
        override val isSharp = true
    },
    E {
        override val scaleOffset = -5
        override val color = Color(0x00, 0xff, 0xff)
    },
    F {
        override val scaleOffset = -4
        override val color = Color(0x00, 0xaa, 0xff)
    },
    F_SHARP {
        override val scaleOffset = -3
        override val color = Color(0xA3, 0x7D, 0xEE, 0xFF)
        override val isSharp = true
    },
    G {
        override val scaleOffset = -2
        override val color = Color(0xff, 0x00, 0xff)
    },
    G_SHARP {
        override val scaleOffset = -1
        override val color = Color(0xff, 0x00, 0xaa)
        override val isSharp = true
    }, ;

    abstract val scaleOffset: Int
    abstract val color: Color
    open val isSharp: Boolean = false
}
