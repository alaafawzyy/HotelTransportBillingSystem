package com.example.swiftprint.database.model.transport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transportcar(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val transportCar: String?,
)
