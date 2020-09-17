package com.ragabz.retrofitrxjavaerrorhandling.di

import com.ragabz.retrofitrxjavaerrorhandling.data.remote.api.ChallengesApi
import com.ragabz.retrofitrxjavaerrorhandling.data.remote.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

    @Provides
    @Singleton
    fun provideChallengesApi(retrofit: Retrofit): ChallengesApi =
        retrofit.create(ChallengesApi::class.java)
}