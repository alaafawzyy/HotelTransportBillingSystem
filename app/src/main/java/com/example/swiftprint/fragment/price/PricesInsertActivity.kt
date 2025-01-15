package com.example.swiftprint.fragment.price

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.swiftprint.R
import com.example.swiftprint.database.model.AppDatabase
import com.example.swiftprint.database.model.price.Prices
import com.example.swiftprint.database.model.price.PricesCar
import com.example.swiftprint.databinding.ActivityPricesInsertBinding
import kotlinx.coroutines.launch

class PricesInsertActivity : AppCompatActivity() {
    lateinit var binding:ActivityPricesInsertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPricesInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val company = intent?.getStringExtra("company")
        val dataType = intent?.getStringExtra("dataType")

        lifecycleScope.launch {
            val dao = AppDatabase.getDatabase(applicationContext).pricesCarDao()
            val carsCount = dao.getCarsCount()

            if (carsCount == 0) { // إذا كانت قاعدة البيانات فارغة
                val initialCars = listOf(
                    PricesCar(
                        null,
                        "\"Coaster \n 16 Pax  ",
                        "Hiace \\n10 Pax  ",
                        "GMC 23 \\n7 Pax ",
                        "GMC \\n7 Pax ",
                        "Family Car\\n VIP \\n5 Pax ",
                        "H1\\n7 Pax",
                        "Ford\n4 Pax ",
                        "Saloon\n4 Pax "
                    )
                   // PricesCar(null, "Car4-TypeA", "Car5-TypeB", "Car6-TypeC", null, null, null, null, null)
                )
                dao.insertCars(initialCars) // إدخال السيارات المبدئية
                Toast.makeText(this@PricesInsertActivity, "تم إدخال البيانات المبدئية.", Toast.LENGTH_SHORT).show()

            }
        }

        getCarFromDataBase()



        binding.btnSave.setOnClickListener {
            val price11 = binding.carType11.text?.toString()?.trim()
            val price12 = binding.carType12.text.toString()?.trim()
            val price13 = binding.carType13.text.toString()?.trim()
            val price14 = binding.carType14.text.toString()?.trim()
            val price15 = binding.carType15.text.toString()?.trim()
            val price16 = binding.carType16.text.toString()?.trim()
            val price17 = binding.carType17.text.toString()?.trim()
            val price18 = binding.carType18.text.toString()?.trim()

            val price21 = binding.carType21.text.toString()?.trim()
            val price22 = binding.carType22.text.toString()?.trim()
            val price23 = binding.carType23.text.toString()?.trim()
            val price24 = binding.carType24.text.toString()?.trim()
            val price25 = binding.carType25.text.toString()?.trim()
            val price26 = binding.carType26.text.toString()?.trim()
            val price27 = binding.carType27.text.toString()?.trim()
            val price28 = binding.carType28.text.toString()?.trim()

            val price31 = binding.carType31.text.toString()?.trim()
            val price32 = binding.carType32.text.toString()?.trim()
            val price33 = binding.carType33.text.toString()?.trim()
            val price34 = binding.carType34.text.toString()?.trim()
            val price35 = binding.carType35.text.toString()?.trim()
            val price36 = binding.carType36.text.toString()?.trim()
            val price37 = binding.carType37.text.toString()?.trim()
            val price38 = binding.carType38.text.toString()?.trim()

            val price41 = binding.carType41.text.toString()?.trim()
            val price42 = binding.carType42.text.toString()?.trim()
            val price43 = binding.carType43.text.toString()?.trim()
            val price44 = binding.carType44.text.toString()?.trim()
            val price45 = binding.carType45.text.toString()?.trim()
            val price46 = binding.carType46.text.toString()?.trim()
            val price47 = binding.carType47.text.toString()?.trim()
            val price48 = binding.carType48.text.toString()?.trim()

            val price51 = binding.carType51.text.toString()?.trim()
            val price52 = binding.carType52.text.toString()?.trim()
            val price53 = binding.carType53.text.toString()?.trim()
            val price54 = binding.carType54.text.toString()?.trim()
            val price55 = binding.carType55.text.toString()?.trim()
            val price56 = binding.carType56.text.toString()?.trim()
            val price57 = binding.carType57.text.toString()?.trim()
            val price58 = binding.carType58.text.toString()?.trim()

            val price61 = binding.carType61.text.toString()?.trim()
            val price62 = binding.carType62.text.toString()?.trim()
            val price63 = binding.carType63.text.toString()?.trim()
            val price64 = binding.carType64.text.toString()?.trim()
            val price65 = binding.carType65.text.toString()?.trim()
            val price66 = binding.carType66.text.toString()?.trim()
            val price67 = binding.carType67.text.toString()?.trim()
            val price68 = binding.carType68.text.toString()?.trim()

            val price71 = binding.carType71.text.toString()?.trim()
            val price72 = binding.carType72.text.toString()?.trim()
            val price73 = binding.carType73.text.toString()?.trim()
            val price74 = binding.carType74.text.toString()?.trim()
            val price75 = binding.carType75.text.toString()?.trim()
            val price76 = binding.carType76.text.toString()?.trim()
            val price77 = binding.carType77.text.toString()?.trim()
            val price78 = binding.carType78.text.toString()?.trim()

            val price81 = binding.carType81.text.toString()?.trim()
            val price82 = binding.carType82.text.toString()?.trim()
            val price83 = binding.carType83.text.toString()?.trim()
            val price84 = binding.carType84.text.toString()?.trim()
            val price85 = binding.carType85.text.toString()?.trim()
            val price86 = binding.carType86.text.toString()?.trim()
            val price87 = binding.carType87.text.toString()?.trim()
            val price88 = binding.carType88.text.toString()?.trim()

            val prices1 = Prices(
                id = null,
                companyName = company,
                type = dataType,
                direction = "Jeddah airport to Makkah",
                pricecar1 = price11,
                pricecar2 = price12,
                pricecar3 = price13,
                pricecar4 = price14,
                pricecar5 = price15,
                pricecar6 = price16,
                pricecar7 = price17,
                pricecar8 = price18
            )

            val prices2 = Prices(
                id = null,
                companyName = company,
                type = dataType,
                direction = "Madena airport to hotels ",
                pricecar1 = price21,
                pricecar2 = price22,
                pricecar3 = price23,
                pricecar4 = price24,
                pricecar5 = price25,
                pricecar6 = price26,
                pricecar7 = price27,
                pricecar8 = price28
            )

            val prices3 = Prices(
                id = null,
                companyName = company,
                type = dataType,
                direction = "Taif airport to Makkah ",
                pricecar1 = price31,
                pricecar2 = price32,
                pricecar3 = price33,
                pricecar4 = price34,
                pricecar5 = price35,
                pricecar6 = price36,
                pricecar7 = price37,
                pricecar8 = price38
            )

            val prices4 = Prices(
                id = null, companyName = company, type = dataType, direction = "Makkah To Madena",
                pricecar1 = price41, pricecar2 = price42, pricecar3 = price43, pricecar4 = price44,
                pricecar5 = price45, pricecar6 = price46, pricecar7 = price47, pricecar8 = price48
            )

            val prices5 = Prices(
                id = null,
                companyName = company,
                type = dataType,
                direction = "Makkah Train Station to",
                pricecar1 = price51,
                pricecar2 = price52,
                pricecar3 = price53,
                pricecar4 = price54,
                pricecar5 = price55,
                pricecar6 = price56,
                pricecar7 = price57,
                pricecar8 = price58
            )

            val prices6 = Prices(
                id = null,
                companyName = company,
                type = dataType,
                direction = "Madena Train Station",
                pricecar1 = price61,
                pricecar2 = price62,
                pricecar3 = price63,
                pricecar4 = price64,
                pricecar5 = price65,
                pricecar6 = price66,
                pricecar7 = price67,
                pricecar8 = price68
            )

            val prices7 = Prices(
                id = null, companyName = company, type = dataType, direction = "Jeddah to Airport",
                pricecar1 = price71, pricecar2 = price72, pricecar3 = price73, pricecar4 = price74,
                pricecar5 = price75, pricecar6 = price76, pricecar7 = price77, pricecar8 = price78
            )

            val prices8 = Prices(
                id = null, companyName = company, type = dataType, direction = "Mazara",
                pricecar1 = price81, pricecar2 = price82, pricecar3 = price83, pricecar4 = price84,
                pricecar5 = price85, pricecar6 = price86, pricecar7 = price87, pricecar8 = price88
            )

            lifecycleScope.launch {
                try {
                    AppDatabase.getDatabase(applicationContext)
                        .pricesDao()
                        .insertPrices(
                            listOf(
                                prices1, prices2, prices3, prices4,
                                prices5, prices6, prices7, prices8
                            )
                        )


                    Toast.makeText(
                        this@PricesInsertActivity,
                        "تم حفظ الاسعار بنجاح",
                        Toast.LENGTH_SHORT
                    ).show()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@PricesInsertActivity,
                        "حدث خطأ أثناء الحفظ: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }


        }

    private fun getCarFromDataBase() {
        val carDao = AppDatabase.getDatabase(applicationContext).pricesCarDao()


        carDao.getAllCars().observe(this) { carsList ->
            if (carsList.isNotEmpty()) {

                val car = carsList[0]

                binding.cartype1.text = car.car1?.toEditable()
                binding.cartype2.text = car.car2?.toEditable()
                binding.cartype3.text = car.car3?.toEditable()
                binding.cartype4.text = car.car4?.toEditable()
                binding.cartype5.text = car.car5?.toEditable()
                binding.cartype6.text = car.car6?.toEditable()
                binding.cartype7.text = car.car7?.toEditable()
                binding.cartype8.text = car.car8?.toEditable()
            }
        }
    }


            private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


}
