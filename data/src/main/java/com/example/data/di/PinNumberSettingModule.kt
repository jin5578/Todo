package com.example.data.di

import com.example.data.usecase.pinnumbersetting.GetPinNumberUseCaseImpl
import com.example.domain.usecase.pinnumbersetting.GetPinNumberUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class PinNumberSettingModule {
    @Binds
    abstract fun bindGetPinNumberUseCase(uc: GetPinNumberUseCaseImpl): GetPinNumberUseCase
}