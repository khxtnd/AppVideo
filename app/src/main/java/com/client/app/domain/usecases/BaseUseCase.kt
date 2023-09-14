package com.client.app.domain.usecases

interface BaseUseCase <P,R>{
    suspend fun invoke(param:P):R
}