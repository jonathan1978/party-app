package com.creativeapps.partyapp.data.repositories

import com.creativeapps.partyapp.data.network.MyApi
import com.creativeapps.partyapp.data.network.responses.AuthResponse

import retrofit2.Response

class UserRepository {

        suspend fun userLogin(email: String, password: String) : Response<AuthResponse> {
            return MyApi().userLogin(email, password)
    }
}