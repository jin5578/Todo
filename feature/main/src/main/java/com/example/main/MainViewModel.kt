package com.example.main

import androidx.lifecycle.ViewModel
import com.example.model.Theme
import com.example.model.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    val theme = flow<Theme> { Theme(ThemeType.SYSTEM) }
}