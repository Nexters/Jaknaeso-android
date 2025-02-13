package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.Characters

interface CharacterRepository {
    suspend fun getCharacters(memberId: Int): LoopyResult<Characters>
}
