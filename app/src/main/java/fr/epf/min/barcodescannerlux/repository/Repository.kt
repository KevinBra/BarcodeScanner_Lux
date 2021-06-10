package fr.epf.min.barcodescannerlux.repository

import fr.epf.min.barcodescannerlux.API.RetrofitInstance
import fr.epf.min.barcodescannerlux.model.Post

class Repository {

    suspend fun getPost(): Post {
        return RetrofitInstance.api.getPost()
    }
}