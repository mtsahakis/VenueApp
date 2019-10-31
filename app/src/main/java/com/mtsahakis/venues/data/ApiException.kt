package com.mtsahakis.venues.data


import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber

class ApiException private constructor(
    cause: Throwable,
    val errorDetail: String?
) : RuntimeException(cause.message, cause) {

    companion object {

        fun build(httpException: HttpException, gson: Gson): ApiException {
            val response = httpException.response()

            if (response != null) {
                val errorBody = response.errorBody()
                var errorDetail: String? = null
                if (errorBody != null) {
                    try {
                        val rawResponse = errorBody.string()
                        val apiResponse =
                            gson.fromJson<ApiResponse>(rawResponse, ApiResponse::class.java)
                        errorDetail = apiResponse.meta.errorDetail
                    } catch (e: Exception) {
                        Timber.e(e, "build() could not parse HttpException error response")
                    }
                }

                return ApiException(httpException, errorDetail)
            } else {
                return ApiException(httpException, null)
            }
        }
    }

}
