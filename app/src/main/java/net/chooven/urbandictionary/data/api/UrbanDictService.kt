package net.chooven.urbandictionary.data.api

import io.reactivex.Single
import net.chooven.urbandictionary.data.model.UrbanDictResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanDictService {
    @GET("/define")
    fun getDefinitions(
        @Query("term") term: String
    ): Single<UrbanDictResponse>

}