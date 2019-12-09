package com.creativeapps.partyapp.ui.auth

import com.creativeapps.partyapp.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}