package com.example.domain.usecase.pinnumbersetting

interface SetPinNumberUseCase {
    suspend operator fun invoke(pinNumber: String): Result<Unit>
}