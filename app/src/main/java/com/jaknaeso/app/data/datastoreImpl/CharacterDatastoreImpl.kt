package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.RefreshTokenManager
import com.jaknaeso.app.data.datastore.CharacterDatastore
import com.jaknaeso.app.data.entity.ErrorData
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.response.CharacterDetailResponse
import com.jaknaeso.app.data.entity.response.CharacterReportResponse
import com.jaknaeso.app.data.entity.response.Characters
import com.jaknaeso.app.data.service.CharacterService
import com.skydoves.sandwich.exceptions.NoContentException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class CharacterDatastoreImpl @Inject constructor(
    private val characterService: CharacterService,
    private val refreshTokenManager: RefreshTokenManager
) : CharacterDatastore {
    override suspend fun getCharacters(memberId: Int): LoopyResult<Characters>? {
        var result: LoopyResult<Characters>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                characterService.getCharacters(memberId).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = LoopyResult(
                        result = ResponseResult.ERROR.name,
                        data = null,
                        error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                    )
                }
            }

            characterService.getCharacters(memberId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = LoopyResult(
                    result = ResponseResult.ERROR.name,
                    data = null,
                    error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                )
                if (this.response.code() == 401) {
                    refreshTokenManager.handleTokenRefresh(
                        retryCall = suspend { retryCall() },
                        onRefreshFailed = {
                            result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                        }
                    )
                }
            }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }

    override suspend fun getLatestCharacter(memberId: String): LoopyResult<CharacterDetailResponse>? {
        var result: LoopyResult<CharacterDetailResponse>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                characterService.getLatestCharacter(memberId).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = LoopyResult(
                        result = ResponseResult.ERROR.name,
                        data = null,
                        error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                    )
                }
            }

            characterService.getLatestCharacter(memberId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = LoopyResult(
                    result = ResponseResult.ERROR.name,
                    data = null,
                    error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                )
                if (this.response.code() == 401) {
                    refreshTokenManager.handleTokenRefresh(
                        retryCall = suspend { retryCall() },
                        onRefreshFailed = {
                            result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                        }
                    )
                }
            }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }

    override suspend fun getCharacterReport(
        characterId: String,
        memberId: String
    ): LoopyResult<CharacterReportResponse>? {
        var result: LoopyResult<CharacterReportResponse>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                characterService.getCharacterReport(characterId, memberId).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = LoopyResult(
                        result = ResponseResult.ERROR.name,
                        data = null,
                        error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                    )
                }
            }

            characterService.getCharacterReport(characterId, memberId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = LoopyResult(
                    result = ResponseResult.ERROR.name,
                    data = null,
                    error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                )
                if (this.response.code() == 401) {
                    refreshTokenManager.handleTokenRefresh(
                        retryCall = suspend { retryCall() },
                        onRefreshFailed = {
                            result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                        }
                    )
                }
            }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }
}
