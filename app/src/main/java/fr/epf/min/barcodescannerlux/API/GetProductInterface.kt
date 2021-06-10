package fr.epf.min.barcodescannerlux

import fr.epf.min.barcodescannerlux.models.Product
import retrofit2.Response
import retrofit2.http.*

interface GetProductInterface {

    @GET("/api/v0/product/{code}.json")
    suspend fun getProductByCode(@Path("code") code : String): ResponseProduct

    data class ResponseProduct(val code: String, val product: Product)
}