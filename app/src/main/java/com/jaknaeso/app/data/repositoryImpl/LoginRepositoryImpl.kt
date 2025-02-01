package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.model.Response
import com.jaknaeso.app.data.model.ResponseResult
import com.jaknaeso.app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override fun postAccessToken(token: String): Response<Any> {
        return Response(result = ResponseResult.SUCCESS, data = null, error = null)
    }

}
