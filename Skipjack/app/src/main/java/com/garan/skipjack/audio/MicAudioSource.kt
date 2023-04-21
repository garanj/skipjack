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
    private val SAMPLE_SIZE = 4096
    private val SAMPLING_RATE_IN_HZ = 44100
    private val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
    private val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    private val bufferSize =
        AudioRecord.getMinBufferSize(SAMPLING_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT) * 2
    private val sampleBuffer = FloatArray(SAMPLE_SIZE)
    private val readBuffer = ShortArray(512)

    private val yin = FastYin(SAMPLING_RATE_IN_HZ.toFloat(), SAMPLE_SIZE)

    @SuppressLint("MissingPermission")
    override val pitchFlow = channelFlow<PitchDetectionResult> {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw IllegalStateException("Permission!")
        }
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLING_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
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