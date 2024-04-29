package com.example.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.lockscreen.LockScreenActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intentToLockScreen()
    }

    private fun intentToLockScreen() {
        LockScreenActivity.startActivity(this)
    }
}