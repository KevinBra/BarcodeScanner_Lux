package fr.epf.min.barcodescannerlux.API

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.epf.min.barcodescannerlux.GetProductInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductApi {
    private const val BASE_URL: String = "https://world.openfoodfacts.org/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: GetProductInterface by lazy{
        retrofit.create(GetProductInterface::class.java)
    }
}