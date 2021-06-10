package fr.epf.min.barcodescannerlux.API

import fr.epf.min.barcodescannerlux.GetProductInterface
import fr.epf.min.barcodescannerlux.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GetProductInterface by lazy {
        retrofit.create(GetProductInterface::class.java)
    }
}