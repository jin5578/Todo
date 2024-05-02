package com.example.presentation.pinnumbersetting.verification

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.component.TodoButton
import com.example.presentation.component.TodoTextField
import com.example.presentation.theme.TodoTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun VerificationScreen(
    viewModel: VerificationViewModel = hiltViewModel(),
    onNavigateToSetting: () -> Unit
) {
    val context = LocalContext.current

    val state: VerificationScreenState = viewModel.collectAsState().value
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is VerificationSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is VerificationSideEffect.NavigateToSetting -> {
                onNavigateToSetting()
            }
        }
    }

    VerificationScreen(
        pinNumber = state.pinNumber,
        onPinNumberChange = viewModel::onPinNumberChange,
        onVerificationClick = viewModel::onVerificationClick
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun VerificationScreen(
    pinNumber: String,
    onPinNumberChange: (String) -> Unit,
    onVerificationClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
                text = "현재 PIN을 입력하세요",
                style = MaterialTheme.typography.labelSmall
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

            TodoButton(
                modifier = Modifier.fillMaxWidth(),
                text = "확인",
                onClick = onVerificationClick
            )
        }
    }

}

@Preview
@Composable
private fun VerificationScreenPreview() {
    TodoTheme {
        VerificationScreen(
            onNavigateToSetting = {}
        )
    }
}