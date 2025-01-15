package com.example.swiftprint.database.model.operation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey val id: Int? = null,  // يسمح ب null
    val name: String?,
    val model: String?
)