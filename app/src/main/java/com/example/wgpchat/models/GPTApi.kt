package com.example.wgpchat.models

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GPTApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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"
    )
    @POST("/v1/completions")
    fun getCompletion(
        @Body requestBody: GPTReq
    ): Call<GPTRes>
}
