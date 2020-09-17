package com.ragabz.retrofitrxjavaerrorhandling.models

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("name") val name: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("code") val code: Int = 200,
    @SerializedName("className") val className: String = "",
    @SerializedName("errors") val errors: List<Any> = mutableListOf(),
    @SerializedName("data") val data: T? = null,
    var isSuccess: Boolean = true
)

data class OTPResponse(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("message") val message: String
)

data class OtpRequest(@SerializedName("mobile") val mobileNumber: String)

data class AuthenticateRequest(
    @SerializedName("mobile") val mobileNumber: String,
    @SerializedName("otp") val otp: String
)

data class Challenge(
    @SerializedName("achievedDistance") val achievedDistance: Double,
    @SerializedName("awardDescriptionAr") val awardDescriptionAr: String?,
    @SerializedName("awardDescriptionEn") val awardDescriptionEn: String?,
    @SerializedName("awardImageAr") val awardImageAr: String?,
    @SerializedName("awardImageEn") val awardImageEn: String?,
    @SerializedName("awardTitleAr") val awardTitleAr: String?,
    @SerializedName("awardTitleEn") val awardTitleEn: String?,
    @SerializedName("endDate") val endDate: String?,
    @SerializedName("goal") val goal: Double,
    @SerializedName("id") val id: String?,
    @SerializedName("isAchiever") val isAchiever: Boolean,
    @SerializedName("isWinner") val isWinner: Boolean,
    @SerializedName("isEligible") val isEligible: Boolean,
    @SerializedName("startDate") val startDate: String?,
    @SerializedName("participants") val participants: Int
)