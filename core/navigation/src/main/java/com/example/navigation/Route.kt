package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Calendar : Route

    @Serializable
    data object Setting : Route

    @Serializable
    data object AddTask : Route

    @Serializable
    data object CompletedTask : Route

    @Serializable
    data object IncompleteTask : Route

    @Serializable
    data object ThisWeekTask : Route

    @Serializable
    data object AllTask : Route

    @Serializable
    data object EditTask : Route
}