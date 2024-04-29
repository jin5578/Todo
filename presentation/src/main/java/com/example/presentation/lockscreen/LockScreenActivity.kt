package com.example.presentation.lockscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.main.MainActivity
import com.example.presentation.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                LockScreen(
                    onIntentToMain = { intentToMain() }
                )
            }
        }
    }

    private fun intentToMain() {
        MainActivity.startActivity(this)
    }

    companion object {
        private fun getIntent(context: Context): Intent =
            Intent(context, LockScreenActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }

        fun startActivity(context: Context) {
            context.startActivity(getIntent(context))
        }
    }
}