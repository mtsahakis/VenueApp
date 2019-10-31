package com.mtsahakis.venues.utils


import com.google.gson.GsonBuilder
import com.mtsahakis.venues.data.ApiException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

object RetrofitHelper {

    fun apiExceptionForInvalidLocation(): ApiException {
        return createApiException(httpExceptionInvalidLocation())
    }

    fun apiExceptionNoErrorDetail(): ApiException {
        return createApiException(httpExceptionNoErrorDetail())
    }

    private fun httpExceptionInvalidLocation(): HttpException {
        val body =
            "{\"meta\":{\"code\":400,\"errorType\":\"failed_geocode\"" +
                    ",\"errorDetail\":\"Couldn't geocode param near: foo\"},\"response\":{}}"
        return createHttpException(body)
    }

    private fun httpExceptionNoErrorDetail(): HttpException {
        val body =
            "{\"meta\":{\"code\":400,\"errorType\":\"failed_geocode\"},\"response\":{}}"
        return createHttpException(body)
    }

    private fun createHttpException(body: String): HttpException {
        val response = Response.error<ResponseBody>(
            400,
            body.toResponseBody("application/json".toMediaTypeOrNull())
        )
        return HttpException(response)
    }

    private fun createApiException(httpException: HttpException): ApiException {
        val gson = GsonBuilder().create()
        return ApiException.build(httpException, gson)
    }

}
