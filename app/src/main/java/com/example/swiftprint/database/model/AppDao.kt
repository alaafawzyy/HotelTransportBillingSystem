package com.example.swiftprint.database.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

@Dao
interface CarsDao {
    @Insert
    suspend fun insertVehicle(vehicle: Vehicle): Long

    @Query("SELECT * FROM vehicles WHERE name = :vehicleName")
    fun getCarByName(vehicleName: String): LiveData<Vehicle>

    @Query("SELECT name FROM vehicles")
    fun getAllVehicleNames(): LiveData<List<String>>


    @Query("DELETE FROM vehicles WHERE name = :vehicleName")
    fun deleteCarByName(vehicleName: String)

}


@Dao
interface DriverDao {
    @Insert
    suspend fun insertDriver(driver: Driver)

    @Query("SELECT * FROM Driver WHERE name = :driverName")
    fun getDriversByName(driverName: String): LiveData<Driver>

    @Query("SELECT name FROM Driver")
    fun getAllDriverNames(): LiveData<List<String>>

    @Query("DELETE FROM Driver WHERE name = :driverName")
    fun deleteDriverByName(driverName: String)
}

@Dao
interface TransportCarDao {
    @Insert
    suspend fun insertTransportCar(car: Transportcar)

    @Query("SELECT transportCar  FROM Transportcar")
    fun getAllTransportCarNames(): LiveData<List<String>>

    @Query("DELETE FROM Transportcar WHERE transportCar = :transportcar")
    fun deleteTransportCarByName(transportcar: String)
}

@Dao
interface InvoiceTailDao {
    @Insert
    suspend fun insertInvoiceTail(invoiceTail: InvoiceTail)

    @Query("SELECT * FROM InvoiceTail WHERE country = :countryName")
    fun getInvoiceTailByCountry(countryName: String): LiveData<InvoiceTail>

    @Query("SELECT  country FROM InvoiceTail")
    fun getAllInvoiceTail(): LiveData<List<String>>

    @Query("DELETE FROM InvoiceTail WHERE country =:countryName")
    fun deleteInvoiceTailByCountryName(countryName: String)

    @Query("SELECT * FROM InvoiceTail ORDER BY id DESC LIMIT 1")
    fun getLastInvoiceTail(): LiveData<InvoiceTail>
}


@Dao
interface InvoiceSubjectDao {
    @Insert
    suspend fun insertInvoiceSubject(invoiceSubject: InvoiceSubject)

    @Query("SELECT invoiceSubject  FROM InvoiceSubject")
    fun getAllInvoiceSubject(): LiveData<List<String>>

    @Query("DELETE FROM InvoiceSubject WHERE invoiceSubject = :invoiceSubject")
    fun deleteInvoiceSubjectByName(invoiceSubject: String)
}

@Dao
interface InvoiceDetailstDao {
    @Insert
    suspend fun insertInvoiceDetails(invoiceDetails: InvoiceDetails)

    @Query("SELECT invoiceDetails  FROM InvoiceDetails")
    fun getAllInvoiceDetails(): LiveData<List<String>>

    @Query("DELETE FROM InvoiceDetails WHERE invoiceDetails = :invoiceDetails")
    fun deleteInvoiceDetails(invoiceDetails: String)
}


@Dao
interface CompanyDao {
    @Insert
    suspend fun insertCompany(company: Company)

    @Query("SELECT * FROM Company WHERE name = :companyName")
    fun getAllCompanyByName(companyName: String): LiveData<Company>

    @Query("SELECT name FROM Company")
    fun getAllCompanyNames(): LiveData<List<String>>


    @Query("DELETE FROM Company WHERE  name= :companyName")
    fun deleteCompanyByName(companyName: String)
}

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insertEmployee(employee: Employee)

    @Query("SELECT * FROM Employee WHERE  companyName= :companyName")
    fun getEmployeeByCompanyName(companyName: String): LiveData<List<Employee>>

    @Query("SELECT name FROM Employee")
    fun getAllEmplyeeNames(): LiveData<List<String>>


    @Query("DELETE FROM Employee WHERE  name= :employeeName")
    fun deleteEmployeeByName(employeeName: String)
}

@Dao
interface PricesDao {

    @Insert
    suspend fun insertPrices(prices: List<Prices>)

    @Query("SELECT * FROM Prices WHERE companyName = :companyName AND type = :type")
    fun getAllTables(companyName: String, type: String): LiveData<List<Prices>>


    @Query("""
        UPDATE Prices 
        SET 
            pricecar1 = :pricecar1, 
            pricecar2 = :pricecar2, 
            pricecar3 = :pricecar3, 
            pricecar4 = :pricecar4, 
            pricecar5 = :pricecar5, 
            pricecar6 = :pricecar6, 
            pricecar7 = :pricecar7 ,
           pricecar8 = :pricecar8
        WHERE companyName = :companyName AND type = :type AND direction = :direction
    """)
    suspend fun updateSinglePrice(
        companyName: String,
        type: String,
        direction: String,
        pricecar1: String?,
        pricecar2: String?,
        pricecar3: String?,
        pricecar4: String?,
        pricecar5: String?,
        pricecar6: String?,
        pricecar7: String?,
        pricecar8: String?
    )

    @Transaction
    suspend fun updatePrices(pricesList: List<Prices>) {
        pricesList.forEach { price ->
            updateSinglePrice(
                companyName = price.companyName ?: "",
                type = price.type ?: "",
                direction = price.direction ?: "",
                pricecar1 = price.pricecar1,
                pricecar2 = price.pricecar2,
                pricecar3 = price.pricecar3,
                pricecar4 = price.pricecar4,
                pricecar5 = price.pricecar5,
                pricecar6 = price.pricecar6,
                pricecar7 = price.pricecar7,
                pricecar8 = price.pricecar8

            )
        }
    }
}




@Dao
interface PricesCarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCars(cars: List<PricesCar>)

    @Query("SELECT * FROM PricesCar")
    fun getAllCars(): LiveData<List<PricesCar>>

    @Query("SELECT COUNT(*) FROM PricesCar")
    suspend fun getCarsCount(): Int

    @Update
    suspend fun updateCar(car: PricesCar)
}