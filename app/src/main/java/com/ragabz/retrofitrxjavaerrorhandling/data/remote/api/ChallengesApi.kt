package com.ragabz.retrofitrxjavaerrorhandling.data.remote.api

import com.ragabz.retrofitrxjavaerrorhandling.models.BaseResponse
import com.ragabz.retrofitrxjavaerrorhandling.models.Challenge
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChallengesApi {
    // challenge list with pagination
    @GET("challenges")
    fun requestChallenges(
        @Query("page") page: Int
    ): Single<Response<BaseResponse<List<Challenge>>>>
}