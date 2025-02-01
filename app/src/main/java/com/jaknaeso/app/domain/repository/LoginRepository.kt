package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.model.Response

interface LoginRepository {
    fun postAccessToken(token: String): Response<Any>
}
