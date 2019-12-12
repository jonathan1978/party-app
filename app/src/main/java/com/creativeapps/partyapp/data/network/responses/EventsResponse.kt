package com.creativeapps.partyapp.data.network.responses

import com.creativeapps.partyapp.data.db.entities.Event

data class EventsResponse (
        val isSuccessful: Boolean,
        val events: List<Event>
)