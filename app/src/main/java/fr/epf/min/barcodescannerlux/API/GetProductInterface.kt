package fr.epf.min.barcodescannerlux

import fr.epf.min.barcodescannerlux.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface GetProductInterface {

    @GET("/api/v0/product/12688403")
    suspend fun getPost(): Post

}