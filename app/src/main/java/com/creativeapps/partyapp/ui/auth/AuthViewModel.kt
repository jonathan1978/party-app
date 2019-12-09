package com.creativeapps.partyapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.creativeapps.partyapp.data.repositories.UserRepository
import com.creativeapps.partyapp.util.ApiException
import com.creativeapps.partyapp.util.Coroutines

class AuthViewModel : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Email or Password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = UserRepository().userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }

        }
    }
}