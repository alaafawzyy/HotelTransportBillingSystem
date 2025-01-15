package com.example.swiftprint.database.model.price

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class PricesCar(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val car1:String?,
    val car2:String?,
    val car3:String?,
    val car4:String?,
    val car5:String?,
    val car6:String?,
    val car7:String?,
    val car8:String?,
)
