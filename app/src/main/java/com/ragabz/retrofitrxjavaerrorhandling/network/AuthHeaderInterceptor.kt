package com.ragabz.retrofitrxjavaerrorhandling.network

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthHeaderInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJpYXQiOjE1OTk1MjI2MDgsImV4cCI6MTYwNDcwNjYwOCwiYXVkIjoiaHR0cHM6Ly9hcGkubWFyYXBob25lLmxheXdhZ2lmLmNvbSIsImlzcyI6Ik1hcmFwaG9uZSIsInN1YiI6IjE0NyIsImp0aSI6ImFhMDZiMjk0LTNmN2QtNDRlNy1iMjkxLWY1MTgyMWNjNWEwMyJ9.zRGndtodiAIw8uX6G1oHQmiuD01Cg6lPwchRq53DPlQ"
        var original = chain.request()
        val url = original.newBuilder()
            .header("Accept-Language", "en")
            .build()
        if (original.header("No-Authentication") == null && !TextUtils.isEmpty(token)) {
            original = original.newBuilder().addHeader("Authorization", "Bearer $token").build()
            return chain.proceed(original)
        }
        return chain.proceed(url)
    }
}
