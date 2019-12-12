package com.creativeapps.partyapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.creativeapps.partyapp.data.db.AppDatabase
import com.creativeapps.partyapp.data.db.entities.Event
import com.creativeapps.partyapp.data.network.MyApi
import com.creativeapps.partyapp.data.network.SafeApiRequest
import com.creativeapps.partyapp.data.preferences.PreferenceProvider
import com.creativeapps.partyapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

class EventsRepository(
        private val api: MyApi,
        private val db: AppDatabase,
        private val prefs: PreferenceProvider
) : SafeApiRequest() {
    private val events = MutableLiveData<List<Event>>()

    init {
        events.observeForever {
            saveEvents(it)
        }
    }

    suspend fun getEvents(): LiveData<List<Event>> {
        return withContext(Dispatchers.IO) {
            fetchEvents()
            db.getEventDao().getEvents()
        }
    }

    private suspend fun fetchEvents() {
        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest { api.getEvents() }
                events.postValue(response.events)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    private fun saveEvents(events: List<Event>) {
        Coroutines.io {
            prefs.saveLastSavedAt(LocalDateTime.now().toString())
            db.getEventDao().saveAllEvents(events)
        }
    }
}