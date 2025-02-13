package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.datastore.CharacterDatastore
import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.Characters
import com.jaknaeso.app.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterDatastore: CharacterDatastore) :
    CharacterRepository {
    override suspend fun getCharacters(memberId: Int): LoopyResult<Characters> {
        return characterDatastore.getCharacters(memberId)
    }
}
