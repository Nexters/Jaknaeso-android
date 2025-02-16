package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.LoopyApiResponse
import com.jaknaeso.app.data.authentication.ResponseHandler
import com.jaknaeso.app.data.datastore.CharacterDatastore
import com.jaknaeso.app.data.entity.ErrorData
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.response.Characters
import com.jaknaeso.app.data.service.CharacterService
import javax.inject.Inject

class CharacterDatastoreImpl @Inject constructor(
    private val characterService: CharacterService,
    private val responseHandler: ResponseHandler
) : CharacterDatastore {
    override suspend fun getCharacters(memberId: Int): LoopyResult<Characters>? {
        val retryResponse = responseHandler.safeApiCall(apiCall = { characterService.getCharacters(memberId) },
            onCompleteTokenRefresh = { null })
        val response = responseHandler.safeApiCall(apiCall = { characterService.getCharacters(memberId) },
            onCompleteTokenRefresh = { retryResponse })
        return when (response) {
            is LoopyApiResponse.Error -> LoopyResult(
                data = null,
                result = ResponseResult.ERROR.name,
                error = ErrorData(code = response.code, message = response.message, data = null)
            )

            is LoopyApiResponse.Success -> response.data
        }
    }

}
