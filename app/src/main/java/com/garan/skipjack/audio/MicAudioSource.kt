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
package com.garan.skipjack.audio

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import be.tarsos.dsp.pitch.FastYin
import be.tarsos.dsp.pitch.PitchDetectionResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

/**
 * Provides pitch detection from the built in microphone.
 */
class MicAudioSource(private val context: Context) : AudioSource {
    private val sampleSize = 4096
    private val samplingRateInHz = 44100
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    private val bufferSize =
        AudioRecord.getMinBufferSize(samplingRateInHz, channelConfig, audioFormat) * 2
    private val sampleBuffer = FloatArray(sampleSize)
    private val readBuffer = ShortArray(512)

    private val yin = FastYin(samplingRateInHz.toFloat(), sampleSize)

    @SuppressLint("MissingPermission")
    override val pitchFlow = channelFlow<PitchDetectionResult> {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw IllegalStateException("Permission!")
        }
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            samplingRateInHz,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize,
        )

        audioRecord.startRecording()

        launch {
            var read = 0
            var samplePos = 0
            while (true) {
                read += audioRecord.read(readBuffer, read, readBuffer.size - read)

                if (read == readBuffer.size) {
                    for (element in readBuffer) {
                        sampleBuffer[samplePos] = element.toFloat()
                        samplePos++
                    }
                    if (samplePos == sampleBuffer.size) {
                        val pitchResult = yin.getPitch(sampleBuffer)
                        send(pitchResult)
                        samplePos = 0
                    }
                    read = 0
                }
            }
        }
        awaitClose {
            audioRecord.stop()
            audioRecord.release()
        }
    }
}
