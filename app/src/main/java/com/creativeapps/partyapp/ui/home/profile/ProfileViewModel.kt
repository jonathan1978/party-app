package com.creativeapps.partyapp.ui.home.profile

import androidx.lifecycle.ViewModel
import com.creativeapps.partyapp.data.repositories.UserRepository

class ProfileViewModel(
        repository: UserRepository
) : ViewModel() {
    val user = repository.getUser()
}