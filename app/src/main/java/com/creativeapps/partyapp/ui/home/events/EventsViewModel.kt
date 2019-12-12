package com.creativeapps.partyapp.ui.home.events

import androidx.lifecycle.ViewModel
import com.creativeapps.partyapp.data.repositories.EventsRepository
import com.creativeapps.partyapp.util.lazyDeferred

class EventsViewModel(
        repository: EventsRepository
) : ViewModel() {
    val events by lazyDeferred {
        repository.getEvents()
    }
}