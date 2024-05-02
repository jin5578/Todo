package com.example.data.usecase.pinnumbersetting

import com.example.data.datastore.LockDataStore
import com.example.domain.usecase.pinnumbersetting.SetPinNumberUseCase
import javax.inject.Inject

class SetPinNumberUseCaseImpl @Inject constructor(
    private val lockDataStore: LockDataStore
) : SetPinNumberUseCase {
    override suspend fun invoke(pinNumber: String): Result<Unit> = kotlin.runCatching {
        lockDataStore.setPinNumber(pinNumber)
    }
}