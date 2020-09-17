package com.ragabz.retrofitrxjavaerrorhandling.network

import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


class ResponseObservableFunction<T> @Inject constructor(
    private val converter: Converter<ResponseBody, ErrorResponse>
) : Function<Response<T>, Single<T>> {
    override fun apply(response: Response<T>): Single<T> {
        if (response.isSuccessful) return Single.just(response.body())
        return when (response.code()) {
            429 -> Single.error(ApiError.TooManyRequest(response.mapToError(converter)))
            400 -> Single.error(ApiError.BadRequest(response.mapToError(converter)))
            401 -> Single.error(ApiError.Unauthorized(response.mapToError(converter)))
            403 -> Single.error(ApiError.Forbidden(response.mapToError(converter)))
            404 -> Single.error(ApiError.NotFound(response.mapToError(converter)))
            408 -> Single.error(ApiError.NotFound(response.mapToError(converter)))
            409 -> Single.error(ApiError.ConflictError(response.mapToError(converter)))
            500 -> Single.error(
                ApiError.InternalServerError(
                    response.mapToError(converter)
                )
            )
            501 -> Single.error(ApiError.NotImplemented(response.mapToError(converter)))
            502 -> Single.error(ApiError.BadGateway(response.mapToError(converter)))
            else -> Single.just(response.body())
        }
    }
}

fun <T> Single<T>.mapNetworkErrors(): Single<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Single.error(NoNetworkException(error))
            is UnknownHostException -> Single.error(ServerUnreachableException(error))
            is HttpException -> Single.error(HttpCallFailureException(error))
            else -> Single.error(error)
        }
    }


data class ErrorResponse(
    @SerializedName("name")
    var name: String,

    @SerializedName("message")
    override var message: String,

    @SerializedName("code")
    var code: Int,

    @SerializedName("className")
    var className: String,

    @SerializedName("errors")
    var errors: List<Any> = mutableListOf()
) : Exception()

sealed class ApiError : Exception() {
    class TooManyRequest(val errorResponse: ErrorResponse) : ApiError()
    class Unauthorized(val errorResponse: ErrorResponse) : ApiError()
    class Forbidden(val errorResponse: ErrorResponse) : ApiError()
    class NotFound(val errorResponse: ErrorResponse) : ApiError()
    class RequestTimeOut(val errorResponse: ErrorResponse) : ApiError()
    class BadRequest(val errorResponse: ErrorResponse) : ApiError()
    class ConflictError(val errorResponse: ErrorResponse) : ApiError()
    class InternalServerError(val errorResponse: ErrorResponse) : ApiError()
    class NotImplemented(val errorResponse: ErrorResponse) : ApiError()
    class BadGateway(val errorResponse: ErrorResponse) : ApiError()
    class ParsingError(val errorResponse: ErrorResponse) : ApiError()
}

fun <T> Response<T>.mapToError(converter: Converter<ResponseBody, ErrorResponse>): ErrorResponse {
    var errorResponse = ErrorResponse(
        code = this@mapToError.code(),
        message = this.message(),
        className = "",
        name = "",
        errors = mutableListOf()
    )
    return try {
        this.errorBody()?.let {
            converter.convert(it)
        } ?: run {
            errorResponse
        }
    } catch (exception: Exception) {
        errorResponse
    }
}





