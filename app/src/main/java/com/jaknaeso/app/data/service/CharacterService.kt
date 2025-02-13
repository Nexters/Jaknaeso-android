package com.jaknaeso.app.data.service

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("/api/v1/characters")
    suspend fun getCharacters(@Query("memberId") memberId: Int): Response<LoopyResult<Characters>>
}
