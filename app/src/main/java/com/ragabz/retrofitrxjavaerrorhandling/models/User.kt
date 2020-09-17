package com.ragabz.retrofitrxjavaerrorhandling.models

data class User(
    val email: Any,
    val fcmToken: String,
    val firstName: String,
    val gender: String,
    val id: String,
    val ipAddress: String,
    val isNew: Boolean,
    val isNotificationsEnabled: Boolean,
    val language: String,
    val lastName: String,
    val mobile: String
)