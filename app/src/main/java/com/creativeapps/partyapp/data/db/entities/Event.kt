package com.creativeapps.partyapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val event: String,
        val title: String,
        val description: String,
        val thumbnail: String,
        val created_at: String,
        val updated_at: String
)