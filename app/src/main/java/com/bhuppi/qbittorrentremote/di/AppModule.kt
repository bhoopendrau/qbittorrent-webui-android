package com.bhuppi.qbittorrentremote.di

import android.content.Context
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProvider
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProviderImpl
import com.bhuppi.qbittorrentremote.data.remote.api.ApplicationApi
import com.bhuppi.qbittorrentremote.data.remote.api.AuthApi
import com.bhuppi.qbittorrentremote.data.remote.api.TorrentsApi
import com.bhuppi.qbittorrentremote.data.remote.api.TransferApi
import com.bhuppi.qbittorrentremote.data.remote.api.intercepters.AddCookiesInterceptor
import com.bhuppi.qbittorrentremote.data.remote.api.intercepters.BaseUrlInterceptor
import com.bhuppi.qbittorrentremote.data.remote.api.intercepters.ReceivedCookiesInterceptor
import com.bhuppi.qbittorrentremote.data.repository.AuthRepositoryImpl
import com.bhuppi.qbittorrentremote.data.repository.TorrentsRepositoryImpl
import com.bhuppi.qbittorrentremote.data.repository.TransferRepositoryImpl
import com.bhuppi.qbittorrentremote.domain.repository.AuthRepository
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import com.bhuppi.qbittorrentremote.domain.repository.TransferRepository
import com.bhuppi.qbittorrentremote.domain.use_case.auth.AuthUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.AddTorrentUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.DeleteTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.PauseTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.ResumeTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.TorrentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Infrastructure
    @Provides
    @Singleton
    fun providesLocalDataProvider(@ApplicationContext context: Context): LocalDataProvider {
        return LocalDataProviderImpl(context)
    }

    @Provides
    @Singleton
    fun providesBaseUrlInterceptor(localDataProvider: LocalDataProvider): BaseUrlInterceptor {
        return BaseUrlInterceptor(localDataProvider)
    }

    @Provides
    @Singleton
    fun providesAddCookiesInterceptor(localDataProvider: LocalDataProvider): AddCookiesInterceptor {
        return AddCookiesInterceptor(localDataProvider)
    }

    @Provides
    @Singleton
    fun providesReceivedCookiesInterceptor(localDataProvider: LocalDataProvider): ReceivedCookiesInterceptor {
        return ReceivedCookiesInterceptor(localDataProvider)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        baseUrlInterceptor: BaseUrlInterceptor,
        addCookiesInterceptor: AddCookiesInterceptor,
        receivedCookiesInterceptor: ReceivedCookiesInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(baseUrlInterceptor)
            .addInterceptor(addCookiesInterceptor)
            .addInterceptor(receivedCookiesInterceptor)
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API interfaces
    @Provides
    @Singleton
    fun providesAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun providesTorrentsApi(retrofit: Retrofit): TorrentsApi = retrofit.create(TorrentsApi::class.java)

    @Provides
    @Singleton
    fun providesTransferApi(retrofit: Retrofit): TransferApi = retrofit.create(TransferApi::class.java)

    @Provides
    @Singleton
    fun providesApplicationApi(retrofit: Retrofit): ApplicationApi = retrofit.create(ApplicationApi::class.java)

    // Repositories
    @Provides
    @Singleton
    fun providesAuthRepository(authApi: AuthApi): AuthRepository = AuthRepositoryImpl(authApi)

    @Provides
    @Singleton
    fun providesTorrentsRepository(api: TorrentsApi): TorrentsRepository = TorrentsRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesTransferRepository(api: TransferApi): TransferRepository = TransferRepositoryImpl(api)

    // Use cases
    @Provides
    @Singleton
    fun providesAuthUseCase(authRepository: AuthRepository, localDataProvider: LocalDataProvider): AuthUseCase {
        return AuthUseCase(authRepository, localDataProvider)
    }

    @Provides
    @Singleton
    fun providesTorrentsUseCase(repository: TorrentsRepository): TorrentsUseCase = TorrentsUseCase(repository)

    @Provides
    @Singleton
    fun providesPauseTorrentsUseCase(repository: TorrentsRepository): PauseTorrentsUseCase = PauseTorrentsUseCase(repository)

    @Provides
    @Singleton
    fun providesResumeTorrentsUseCase(repository: TorrentsRepository): ResumeTorrentsUseCase = ResumeTorrentsUseCase(repository)

    @Provides
    @Singleton
    fun providesDeleteTorrentsUseCase(repository: TorrentsRepository): DeleteTorrentsUseCase = DeleteTorrentsUseCase(repository)

    @Provides
    @Singleton
    fun providesAddTorrentUseCase(repository: TorrentsRepository): AddTorrentUseCase = AddTorrentUseCase(repository)
}
