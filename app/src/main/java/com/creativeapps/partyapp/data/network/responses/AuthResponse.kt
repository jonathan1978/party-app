package com.creativeapps.partyapp.data.network.responses

import com.creativeapps.partyapp.data.db.entities.User

data class AuthResponse (
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)