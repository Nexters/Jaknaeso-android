package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.model.ApiResponse
import com.jaknaeso.app.data.model.ResponseResult
import com.jaknaeso.app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override fun getMemberToken(token: String): ApiResponse<Any> {
        return ApiResponse(result = ResponseResult.SUCCESS, data = null, error = null)
    }

}
