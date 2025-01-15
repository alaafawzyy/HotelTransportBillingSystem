package com.example.swiftprint.database.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.swiftprint.database.model.operation.Driver
import com.example.swiftprint.database.model.operation.Vehicle
import com.example.swiftprint.database.model.price.Prices
import com.example.swiftprint.database.model.price.PricesCar
import com.example.swiftprint.database.model.transport.Company
import com.example.swiftprint.database.model.transport.Employee
import com.example.swiftprint.database.model.transport.InvoiceDetails
import com.example.swiftprint.database.model.transport.InvoiceSubject
import com.example.swiftprint.database.model.transport.InvoiceTail
import com.example.swiftprint.database.model.transport.Transportcar

@Database(entities = [Vehicle::class, Driver::class,Employee::class,Company::class,
                     Transportcar::class,InvoiceTail::class,InvoiceDetails::class,InvoiceSubject::class
                     ,Prices::class,PricesCar::class], version =7 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun vehicleDao(): CarsDao
  abstract fun driverDao(): DriverDao

  abstract fun employeeDao(): EmployeeDao
  abstract fun companyDao():CompanyDao
  abstract fun transportCarDao():TransportCarDao
  abstract fun invoiceSubjectDao(): InvoiceSubjectDao
  abstract fun invoiceDetailsDao(): InvoiceDetailstDao
  abstract fun invoiceTailDao(): InvoiceTailDao
  abstract fun pricesDao():PricesDao
  abstract fun pricesCarDao():PricesCarDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "app_database"
        ).build()
        INSTANCE = instance
        instance
      }
    }
  }
}
