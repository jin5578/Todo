package com.example.presentation.pinnumbersetting

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.domain.usecase.pinnumbersetting.GetPinNumberUseCase
import com.example.presentation.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PinNumberSettingActivity : AppCompatActivity() {
    @Inject
    lateinit var getPinNumberUseCase: GetPinNumberUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val hasPinNumber = !getPinNumberUseCase().isNullOrEmpty()

            val startDestination = if (hasPinNumber) {
                PinNumberSettingRoute.VERIFICATION
            } else {
                PinNumberSettingRoute.SETTING
            }

            setContent {
                TodoTheme {
                    PinNumberSettingNavHost(
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}