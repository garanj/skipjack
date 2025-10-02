package com.garan.skipjack

import android.content.Context
import com.garan.skipjack.audio.MicAudioSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {
    @Singleton
    @Provides
    fun providesTuningRepository(micAudioSource: MicAudioSource) = TuningRepository(micAudioSource)

    @Singleton
    @Provides
    fun providesAudioSource(@ApplicationContext appContext: Context) = MicAudioSource(appContext)
}