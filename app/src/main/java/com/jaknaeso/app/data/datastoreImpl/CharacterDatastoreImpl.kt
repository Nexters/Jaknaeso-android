package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.RefreshTokenManager
import com.jaknaeso.app.data.datastore.CharacterDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.response.CharacterDetailResponse
import com.jaknaeso.app.data.entity.response.Characters
import com.jaknaeso.app.data.service.CharacterService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class CharacterDatastoreImpl @Inject constructor(
    private val characterService: CharacterService,
    private val refreshTokenManager: RefreshTokenManager
) : CharacterDatastore {
    override suspend fun getCharacters(memberId: Int): LoopyResult<Characters>? {
        var result: LoopyResult<Characters>? = LoopyResult(result = null, data = null, error = null)
        suspend fun retryCall() {
            characterService.getCharacters(memberId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = this.response.body()
            }
        }

        characterService.getCharacters(memberId).suspendOnSuccess {
            result = this.data
        }.suspendOnError {
            result = this.response.body()
            if (this.response.code() == 401) {
                refreshTokenManager.handleTokenRefresh(
                    retryCall = suspend { retryCall() },
                    onRefreshFailed = {
                        result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                    }
                )
            }
        }
        return result
    }

    override suspend fun getLatestCharacter(memberId: String): LoopyResult<CharacterDetailResponse>? {
        var result: LoopyResult<CharacterDetailResponse>? = LoopyResult(result = null, data = null, error = null)
        suspend fun retryCall() {
            characterService.getLatestCharacter(memberId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = this.response.body()
            }
        }

        characterService.getLatestCharacter(memberId).suspendOnSuccess {
            result = this.data
        }.suspendOnError {
            result = this.response.body()
            if (this.response.code() == 401) {
                refreshTokenManager.handleTokenRefresh(
                    retryCall = suspend { retryCall() },
                    onRefreshFailed = {
                        result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                    }
                )
            }
        }
        return result
    }
}
