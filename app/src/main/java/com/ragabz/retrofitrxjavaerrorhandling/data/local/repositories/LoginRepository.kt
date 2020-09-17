package com.ragabz.retrofitrxjavaerrorhandling.data.local.repositories

import com.ragabz.retrofitrxjavaerrorhandling.network.ErrorResponse
import com.ragabz.retrofitrxjavaerrorhandling.network.ResponseObservableFunction
import com.ragabz.retrofitrxjavaerrorhandling.data.remote.api.LoginApi
import com.ragabz.retrofitrxjavaerrorhandling.network.mapNetworkErrors
import com.ragabz.retrofitrxjavaerrorhandling.models.AuthenticateRequest
import com.ragabz.retrofitrxjavaerrorhandling.models.LoginResponse
import com.ragabz.retrofitrxjavaerrorhandling.models.OTPResponse
import com.ragabz.retrofitrxjavaerrorhandling.models.OtpRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Converter
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginApi: LoginApi,
    private val converter: Converter<ResponseBody, ErrorResponse>
) {


    fun requestOtp(mobile: String): Single<OTPResponse> {
        return loginApi.otp(OtpRequest(mobileNumber = mobile))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .mapNetworkErrors()
            .flatMap(ResponseObservableFunction(converter))

    }

    fun authenticate(mobileNumber: String, otp: String): Single<LoginResponse> {
        return loginApi.authenticate(AuthenticateRequest(mobileNumber, otp))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .mapNetworkErrors()
            .flatMap(ResponseObservableFunction(converter))
    }

}

