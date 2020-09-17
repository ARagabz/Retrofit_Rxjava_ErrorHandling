package com.ragabz.retrofitrxjavaerrorhandling.data.local.repositories

import com.ragabz.retrofitrxjavaerrorhandling.network.ErrorResponse
import com.ragabz.retrofitrxjavaerrorhandling.network.ResponseObservableFunction
import com.ragabz.retrofitrxjavaerrorhandling.data.remote.api.ChallengesApi
import com.ragabz.retrofitrxjavaerrorhandling.network.mapNetworkErrors
import com.ragabz.retrofitrxjavaerrorhandling.models.BaseResponse
import com.ragabz.retrofitrxjavaerrorhandling.models.Challenge
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Converter
import javax.inject.Inject

class ChallengesRepository @Inject constructor(
    private val api: ChallengesApi,
    private val converter: Converter<ResponseBody, ErrorResponse>
) {


    fun requestChallengesList(): Single<BaseResponse<List<Challenge>>> {
        return api.requestChallenges(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .mapNetworkErrors()
            .flatMap(ResponseObservableFunction(converter))

    }

}