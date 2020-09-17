package com.ragabz.retrofitrxjavaerrorhandling.data.remote.api

import com.ragabz.retrofitrxjavaerrorhandling.models.*
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("otp")
    fun otp(@Body request: OtpRequest): Single<Response<OTPResponse>>

    @POST("authentication")
    fun authenticate(@Body request: AuthenticateRequest): Single<Response<LoginResponse>>
}