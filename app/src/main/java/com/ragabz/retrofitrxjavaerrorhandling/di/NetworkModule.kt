package com.ragabz.retrofitrxjavaerrorhandling.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import com.ragabz.retrofitrxjavaerrorhandling.network.AuthHeaderInterceptor
import com.ragabz.retrofitrxjavaerrorhandling.BuildConfig
import com.ragabz.retrofitrxjavaerrorhandling.network.ErrorResponse
import com.ragabz.retrofitrxjavaerrorhandling.common.utils.NetworkTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        authHeaderInterceptor: AuthHeaderInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                // enable log for debug mode only
                addInterceptor(loggingInterceptor)
                addInterceptor(CurlInterceptor(Loggable { message -> Timber.v("Ok2Curl $message") }))
                addInterceptor(chuckerInterceptor)
            }
            addInterceptor(authHeaderInterceptor)
            connectTimeout(NetworkTime.CONNECTION.value, TimeUnit.MINUTES)
            readTimeout(NetworkTime.READ.value, TimeUnit.MINUTES)
            writeTimeout(NetworkTime.WRITE.value, TimeUnit.MINUTES)
            retryOnConnectionFailure(true)
        }.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maraphone.martech.ws/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideErrorResponseBodyConverter(retrofit: Retrofit): Converter<ResponseBody, ErrorResponse> {
        return retrofit.responseBodyConverter(ErrorResponse::class.java, arrayOfNulls(0))
    }
}