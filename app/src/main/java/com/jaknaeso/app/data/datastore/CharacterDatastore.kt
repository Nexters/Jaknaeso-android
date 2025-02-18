package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.CharacterDetailResponse
import com.jaknaeso.app.data.entity.response.CharacterGraphValueResponse
import com.jaknaeso.app.data.entity.response.Characters

interface CharacterDatastore {
    suspend fun getCharacters(memberId: Int): LoopyResult<Characters>?
    suspend fun getLatestCharacter(memberId: String): LoopyResult<CharacterDetailResponse>?
    suspend fun getCharacterReport(characterId: String, memberId: String): LoopyResult<CharacterDetailResponse>?
    suspend fun getCharacterGraphValue(characterId: String, memberId: String): LoopyResult<CharacterGraphValueResponse>?
}
