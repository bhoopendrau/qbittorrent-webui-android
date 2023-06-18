package com.bhuppi.qbittorrentremote.di

import com.bhuppi.qbittorrentremote.data.remote.api.TorrentsApi
import com.bhuppi.qbittorrentremote.data.remote.api.intercepters.ReceivedCookiesInterceptor
import com.bhuppi.qbittorrentremote.data.repository.TorrentsRepositoryImpl
import com.bhuppi.qbittorrentremote.domain.repository.TorrentRepository
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.TorrentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TorrentsModule {
    @Provides
    @Singleton
    fun providesTorrentsApi (receivedCookiesInterceptor: ReceivedCookiesInterceptor): TorrentsApi {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(receivedCookiesInterceptor)
            .build()
        return Retrofit
            .Builder()
            .baseUrl("https://torrents.bhuppi.in")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TorrentsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTorrentsRepository(api: TorrentsApi): TorrentRepository {
        return TorrentsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesAuthUseCase(repository: TorrentRepository): TorrentsUseCase {
        return TorrentsUseCase(repository)
    }
}