package com.bhuppi.qbittorrentremote.di

import android.app.Application
import android.content.Context
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProvider
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProviderImpl
import com.bhuppi.qbittorrentremote.data.remote.api.AuthApi
import com.bhuppi.qbittorrentremote.data.remote.api.intercepters.AddCookiesInterceptor
import com.bhuppi.qbittorrentremote.data.remote.api.intercepters.ReceivedCookiesInterceptor
import com.bhuppi.qbittorrentremote.data.repository.AuthRepositoryImpl
import com.bhuppi.qbittorrentremote.domain.repository.AuthRepository
import com.bhuppi.qbittorrentremote.domain.use_case.auth.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesLocalDataProvider(@ApplicationContext context: Context): LocalDataProvider {
        return LocalDataProviderImpl(context)
    }

    @Provides
    @Singleton
    fun providesAddCookiesInterceptor(localDataProvider: LocalDataProvider): AddCookiesInterceptor {
        return AddCookiesInterceptor(localDataProvider)
    }

    @Provides
    @Singleton
    fun providesRecievedCookiesInterceptor(localDataProvider: LocalDataProvider): ReceivedCookiesInterceptor {
        return ReceivedCookiesInterceptor(localDataProvider)
    }

    @Provides
    @Singleton
    fun provideAuthApi (receivedCookiesInterceptor: ReceivedCookiesInterceptor): AuthApi {
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
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun providesAuthUseCase(authRepository: AuthRepository): AuthUseCase {
        return AuthUseCase(authRepository)
    }
}