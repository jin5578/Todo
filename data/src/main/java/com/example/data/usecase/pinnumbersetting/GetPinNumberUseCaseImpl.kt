package com.example.data.usecase.pinnumbersetting

import com.example.data.datastore.LockDataStore
import com.example.domain.usecase.pinnumbersetting.GetPinNumberUseCase
import javax.inject.Inject

class GetPinNumberUseCaseImpl @Inject constructor(
    private val lockDataStore: LockDataStore
) : GetPinNumberUseCase {
    override suspend fun invoke(): String? {
        return lockDataStore.getPinNumber()
    }
}