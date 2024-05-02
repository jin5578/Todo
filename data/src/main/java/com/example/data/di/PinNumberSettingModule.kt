package com.example.data.di

import com.example.data.usecase.pinnumbersetting.ClearPinNumberUseCaseImpl
import com.example.data.usecase.pinnumbersetting.GetPinNumberUseCaseImpl
import com.example.data.usecase.pinnumbersetting.SetPinNumberUseCaseImpl
import com.example.domain.usecase.pinnumbersetting.ClearPinNumberUseCase
import com.example.domain.usecase.pinnumbersetting.GetPinNumberUseCase
import com.example.domain.usecase.pinnumbersetting.SetPinNumberUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class PinNumberSettingModule {
    @Binds
    abstract fun bindGetPinNumberUseCase(uc: GetPinNumberUseCaseImpl): GetPinNumberUseCase

    @Binds
    abstract fun bindSetPinNumberUseCase(uc: SetPinNumberUseCaseImpl): SetPinNumberUseCase

    @Binds
    abstract fun bindClearPinNumberUseCase(uc: ClearPinNumberUseCaseImpl): ClearPinNumberUseCase
}