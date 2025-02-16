package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.Characters

interface CharacterDatastore {
    suspend fun getCharacters(memberId: Int): LoopyResult<Characters>?
}
