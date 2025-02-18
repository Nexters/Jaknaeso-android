package com.jaknaeso.app.data.service

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.CharacterDetailResponse
import com.jaknaeso.app.data.entity.response.CharacterGraphValueResponse
import com.jaknaeso.app.data.entity.response.Characters
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("/api/v1/characters")
    suspend fun getCharacters(@Query("memberId") memberId: Int): ApiResponse<LoopyResult<Characters>>

    @GET("/api/v1/characters/latest")
    suspend fun getLatestCharacter(@Query("memberId") memberId: String): ApiResponse<LoopyResult<CharacterDetailResponse>>

    @GET("/api/v1/characters/{characterId}")
    suspend fun getCharacterReport(
        @Path("characterId") characterId: String,
        @Query("memberId") memberId: String
    ): ApiResponse<LoopyResult<CharacterDetailResponse>>

    @GET("/api/v1/characters/{characterId}/report")
    suspend fun getCharacterGraphValue(
        @Path("characterId") characterId: String,
        @Query("memberId") memberId: String
    ): ApiResponse<LoopyResult<CharacterGraphValueResponse>>
}
