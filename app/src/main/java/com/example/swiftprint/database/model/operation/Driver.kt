package com.example.swiftprint.database.model.operation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Driver(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String?,
    val idNumber: String?,
    val phoneNumber: String?
)