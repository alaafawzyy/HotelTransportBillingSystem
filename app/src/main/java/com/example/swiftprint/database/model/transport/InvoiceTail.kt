package com.example.swiftprint.database.model.transport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InvoiceTail (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val country: String?,
    val phoneNumber: String?,
    val faxNumber: String?,
    val website: String?
)

