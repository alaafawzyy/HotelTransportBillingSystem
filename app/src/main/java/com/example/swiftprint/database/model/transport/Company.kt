package com.example.swiftprint.database.model.transport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Company(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String?,
    val details: String?
)
