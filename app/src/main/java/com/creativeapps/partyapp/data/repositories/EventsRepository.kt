package com.creativeapps.partyapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.creativeapps.partyapp.data.db.AppDatabase
import com.creativeapps.partyapp.data.db.entities.Event
import com.creativeapps.partyapp.data.network.MyApi
import com.creativeapps.partyapp.data.network.SafeApiRequest
import com.creativeapps.partyapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepository(
        private val api: MyApi,
        private val db: AppDatabase
) : SafeApiRequest() {
    private val events = MutableLiveData<List<Event>>()

    init {
        events.observeForever {
            saveEvents(it)
        }
    }

    suspend fun getEvents(): LiveData<List<Event>>{
        return withContext(Dispatchers.IO){
            fetchEvents()
            db.getEventDao().getEvents()
        }
    }

    private suspend fun fetchEvents() {
        if (isFetchNeeded()) {
            val response = apiRequest { api.getEvents() }
            events.postValue(response.events)
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    private fun saveEvents(events: List<Event>) {
        Coroutines.io {
            db.getEventDao().saveAllEvents(events)
        }
    }
}