package com.example.corina.trackseries.model

data class AccountInfo(
    val accountId: Int? = -1,
    val connected: Int? = 0,
    val email: String? = "email",
    val password: String? = "password",
    val roleId: Int? = -1,
    val username: String? = "username"
)