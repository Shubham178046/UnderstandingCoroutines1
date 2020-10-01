package net.simplifiedcoding.understandingcoroutines.data.network

import net.simplifiedcoding.understandingcoroutines.data.models.Movie
import net.simplifiedcoding.understandingcoroutines.data.models.QuotesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface MyApi {

    @GET("mvvm/quotes")
    suspend fun getQuotes():Response<QuotesResponse>

    @GET("recyclerview/movies")
   suspend fun getMovies():  Response<List<Movie>>

//    @GET("quotes")
//    fun getMovies(): Call<QuotesResponse>

    companion object {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.simplifiedcoding.in/course-apis/")
                .client(okHttpClient)
                .build()
                .create(MyApi::class.java)
        }
    }
}
