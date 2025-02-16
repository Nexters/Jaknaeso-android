package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.CharacterDetailResponse
import com.jaknaeso.app.data.entity.response.Characters

interface CharacterRepository {
    suspend fun getCharacters(memberId: Int): LoopyResult<Characters>?
    suspend fun getLatestCharacter(memberId: String): LoopyResult<CharacterDetailResponse>?
}
