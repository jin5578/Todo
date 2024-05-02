package com.example.presentation.pinnumbersetting.setting

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.component.TodoButton
import com.example.presentation.component.TodoTextField
import com.example.presentation.theme.TodoTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onIntentToMain: () -> Unit
) {
    val context = LocalContext.current

    val state: SettingScreenState = viewModel.collectAsState().value
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is SettingSideEffect.IntentToMain -> {
                onIntentToMain()
            }
        }
    }

    SettingScreen(
        pinNumber = state.pinNumber,
        onPinNumberChange = viewModel::onPinNumberChange,
        onChangeClick = viewModel::onChangeClick,
        onClearClick = viewModel::onClearClick
    )
}

@Composable
private fun SettingScreen(
    pinNumber: String,
    onPinNumberChange: (String) -> Unit,
    onChangeClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "지금 설정한 PIN Number를 꼭 기억하세요.\n 잊어버리면, 앱을 초기화해야 하고, 데이터는 모두 삭제됩니다.",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            TodoTextField(
                modifier = Modifier.fillMaxWidth(),
                value = pinNumber,
                onValueChange = onPinNumberChange,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
            )

            Spacer(modifier = Modifier.height(height = 40.dp))

            Row {
                TodoButton(
                    modifier = Modifier.weight(1f),
                    text = "해제",
                    onClick = onClearClick
                )

                Spacer(modifier = Modifier.width(20.dp))

                TodoButton(
                    modifier = Modifier.weight(1f),
                    text = "변경",
                    onClick = onChangeClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    TodoTheme {
        SettingScreen(
            pinNumber = "",
            onPinNumberChange = {},
            onChangeClick = {},
            onClearClick = {}
        )
    }
}