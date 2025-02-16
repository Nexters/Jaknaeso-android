package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.Characters

interface CharacterRepository {
    suspend fun getCharacters(memberId: Int): LoopyResult<Characters>?
}
