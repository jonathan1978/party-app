package com.creativeapps.partyapp.data.repositories

import com.creativeapps.partyapp.data.network.MyApi
import com.creativeapps.partyapp.data.network.SafeApiRequest
import com.creativeapps.partyapp.data.network.responses.AuthResponse

import retrofit2.Response

class UserRepository : SafeApiRequest(){

        suspend fun userLogin(email: String, password: String) : AuthResponse {
            return apiRequest { MyApi().userLogin(email, password) }
    }
}