package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.Characters

interface CharacterDatastore {
    suspend fun getCharacters(memberId: Int): LoopyResult<Characters>
}
