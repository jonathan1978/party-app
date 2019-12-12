package com.creativeapps.partyapp.data.network

import com.creativeapps.partyapp.data.network.responses.AuthResponse
import com.creativeapps.partyapp.data.network.responses.EventsResponse
import okhttp3.OkHttpClient
import okhttp3.internal.http.BridgeInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
            @Field("email") email: String,
            @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String
    ) : Response<AuthResponse>

    @GET("events")
    suspend fun getEvents() : Response<EventsResponse>


    companion object{
        operator fun invoke(
                networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }
    }
}