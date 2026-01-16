package com.smartfilerenamer.feature.analyzer.di

import com.smartfilerenamer.core.data.repository.ContentAnalyzer
import com.smartfilerenamer.feature.analyzer.ContentAnalyzerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyzerModule {

    @Binds
    @Singleton
    abstract fun bindContentAnalyzer(
        impl: ContentAnalyzerImpl
    ): ContentAnalyzer
}
