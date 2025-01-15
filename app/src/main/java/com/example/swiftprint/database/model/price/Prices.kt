package com.example.swiftprint.database.model.price

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Prices(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val companyName: String?,
    val type: String?,
    val direction:String?,
    val pricecar1:String?,
    val pricecar2:String?,
    val pricecar3:String?,
    val pricecar4:String?,
    val pricecar5:String?,
    val pricecar6:String?,
    val pricecar7:String?,
    val pricecar8:String?,
)
