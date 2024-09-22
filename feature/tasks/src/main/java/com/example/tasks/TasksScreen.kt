package com.example.tasks

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    title: String,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {

}