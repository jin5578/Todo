package com.example.domain.usecase.pinnumbersetting

interface GetPinNumberUseCase {
    suspend operator fun invoke(): String?
}