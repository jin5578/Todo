package com.example.data.usecase.pinnumbersetting

import com.example.data.datastore.LockDataStore
import com.example.domain.usecase.pinnumbersetting.ClearPinNumberUseCase
import javax.inject.Inject

class ClearPinNumberUseCaseImpl @Inject constructor(
    private val lockDataStore: LockDataStore
) : ClearPinNumberUseCase {
    override suspend fun invoke() {
        lockDataStore.clear()
    }
}