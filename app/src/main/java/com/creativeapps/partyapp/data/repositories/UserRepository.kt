package com.creativeapps.partyapp.data.repositories

import com.creativeapps.partyapp.data.db.AppDatabase
import com.creativeapps.partyapp.data.db.entities.User
import com.creativeapps.partyapp.data.network.MyApi
import com.creativeapps.partyapp.data.network.SafeApiRequest
import com.creativeapps.partyapp.data.network.responses.AuthResponse

import retrofit2.Response

class UserRepository(
        private val api: MyApi,
        private val db: AppDatabase
) : SafeApiRequest(){

        suspend fun userLogin(email: String, password: String) : AuthResponse {
            return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}