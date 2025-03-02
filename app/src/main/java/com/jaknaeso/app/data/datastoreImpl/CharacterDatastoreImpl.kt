package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.ApiCallAdapter
import com.jaknaeso.app.data.datastore.CharacterDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.CharacterDetailResponse
import com.jaknaeso.app.data.entity.response.CharacterGraphValueResponse
import com.jaknaeso.app.data.entity.response.Characters
import com.jaknaeso.app.data.service.CharacterService
import javax.inject.Inject

class CharacterDatastoreImpl @Inject constructor(
    private val characterService: CharacterService,
    private val apiCallAdapter: ApiCallAdapter
) : CharacterDatastore {
    override suspend fun getCharacters(memberId: Int): LoopyResult<Characters>? {
        return apiCallAdapter.getApiResult { characterService.getCharacters(memberId) }
    }

    override suspend fun getLatestCharacter(memberId: String): LoopyResult<CharacterDetailResponse>? {
        return apiCallAdapter.getApiResult { characterService.getLatestCharacter(memberId) }
    }

    override suspend fun getCharacterReport(
        characterId: String,
        memberId: String
    ): LoopyResult<CharacterDetailResponse>? {
        return apiCallAdapter.getApiResult { characterService.getCharacterReport(characterId, memberId) }
    }

    override suspend fun getCharacterGraphValue(
        characterId: String,
        memberId: String
    ): LoopyResult<CharacterGraphValueResponse>? {
        return apiCallAdapter.getApiResult { characterService.getCharacterGraphValue(characterId, memberId) }
    }
}
