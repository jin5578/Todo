package com.example.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import com.example.design_system.R as DesignSystemR

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val localContextResources = LocalContext.current.resources

    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                when (throwable) {
                    is UnknownHostException -> localContextResources.getString(DesignSystemR.string.error_message_network)
                    else -> localContextResources.getString(DesignSystemR.string.error_message_unknown)
                }
            )
        }
    }

    val onShowMessageSnackBar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    MainScreen(
        navigator = navigator,
        snackbarHostState = snackbarHostState,
        onShowErrorSnackBar = onShowErrorSnackBar,
        onShowMessageSnackBar = onShowMessageSnackBar,
    )
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    snackbarHostState: SnackbarHostState,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        content = { _ ->
            MainNavHost(
                navigator = navigator,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
}