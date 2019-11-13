package net.chooven.urbandictionary.data

import android.app.Application

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.chooven.urbandictionary.data.api.DateDeserializer
import net.chooven.urbandictionary.data.api.UrbanDictService

import java.io.File
import java.util.Date

import okhttp3.Cache
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Provides [Picasso], [WeatherService] and [IconApi]
 * that are all ready setup and ready to use.
 */
class ApiServicesProvider(application: Application) {
    companion object {
        private const val DISK_CACHE_SIZE: Long = 100 * 1024 * 1024
    }

    /**
     * Instance of the [UrbanDictService] service that is ready to use.
     */
    val urbanDictService: UrbanDictService

    init {
        val client = createOkHttpClient(application)

        urbanDictService = provideUrbanDictRetrofit(client, provideGson()).create(UrbanDictService::class.java)
    }

    private fun provideUrbanDictRetrofit(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
        .client(client)
        .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private fun provideGson() = GsonBuilder()
        .apply { registerTypeAdapter(Date::class.java, DateDeserializer()) }
        .create()
    private fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder()
            .addHeader("User-x-rapidapi-host","mashape-community-urban-dictionary.p.rapidapi.com")
            .addHeader("x-rapidapi-key","94c9d5139dmshb63b608d902bb3dp107c59jsn7f3c80e9e563")
            .build())
    }
    private fun createOkHttpClient(app: Application): OkHttpClient {
        // Install an HTTP cache in the application cache directory.
        val cacheDir = File(app.cacheDir, "http")
        val cache = Cache(cacheDir, DISK_CACHE_SIZE)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val debugInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//            this.level = HttpLoggingInterceptor.Level.HEADERS
//            this.level = HttpLoggingInterceptor.Level.BODY
//        }

        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                //.addInterceptor(headersInterceptor())
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("User-x-rapidapi-host","mashape-community-urban-dictionary.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "94c9d5139dmshb63b608d902bb3dp107c59jsn7f3c80e9e563")
                        .build()
                    chain.proceed(newRequest)
                }
                .build()
    }
}
