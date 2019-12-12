package com.creativeapps.partyapp.ui.home.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.creativeapps.partyapp.data.repositories.EventsRepository

@Suppress("UNCHECKED_CAST")
class EventsViewModelFactory(
        private val repository: EventsRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventsViewModel(repository) as T
    }
}