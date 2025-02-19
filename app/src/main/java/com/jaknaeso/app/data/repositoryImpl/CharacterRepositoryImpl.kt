package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.datastore.CharacterDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.CharacterDetailResponse
import com.jaknaeso.app.data.entity.response.CharacterGraphValueResponse
import com.jaknaeso.app.data.entity.response.Characters
import com.jaknaeso.app.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterDatastore: CharacterDatastore) :
    CharacterRepository {
    override suspend fun getCharacters(memberId: Int): LoopyResult<Characters>? {
        return characterDatastore.getCharacters(memberId)
    }

    override suspend fun getLatestCharacter(memberId: String): LoopyResult<CharacterDetailResponse>? {
        return characterDatastore.getLatestCharacter(memberId)
    }

    override suspend fun getCharacterReport(
        characterId: String,
        memberId: String
    ): LoopyResult<CharacterDetailResponse>? {
        return characterDatastore.getCharacterReport(characterId, memberId)
    }

    override suspend fun getCharacterGraphValue(
        characterId: String,
        memberId: String
    ): LoopyResult<CharacterGraphValueResponse>? {
        return characterDatastore.getCharacterGraphValue(characterId, memberId)
    }
}
