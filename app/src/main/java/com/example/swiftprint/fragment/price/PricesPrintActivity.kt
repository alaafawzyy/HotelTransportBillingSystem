package com.example.swiftprint.fragment.price

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.swiftprint.databinding.ActivityPricesPrintBinding

import androidx.core.content.ContextCompat
import android.graphics.pdf.PdfDocument
import android.text.Editable

import androidx.core.widget.addTextChangedListener
import com.example.swiftprint.database.model.AppDatabase

import java.io.File
import java.io.FileOutputStream


class PricesPrintActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPricesPrintBinding
    private val sharedPrefKey = "PricePreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPricesPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSavedValues()

        getInvoicefromdatabase()
        setupAutoSaveListener()

        val company = intent.getStringExtra("company")
        val dataType = intent.getStringExtra("dataType")
        getTableFromDataBase(company.toString(), dataType.toString())
        getCarFromDataBase()

        binding.logo.setOnClickListener {
            takeScreenshotAndSaveAsPdf()
        }
    }

    private fun getInvoicefromdatabase() {
        val invoiceTailDao =AppDatabase.getDatabase(this).invoiceTailDao()
        invoiceTailDao.getLastInvoiceTail().observe(this, Observer { invoiceTail ->
            invoiceTail?.let {
                binding.country.text = it.country?.toEditable()
                binding.fax.text = it.faxNumber?.toEditable()
                binding.phone.text = it.phoneNumber?.toEditable()
                binding.website.text = it.website?.toEditable()
            }
        })
    }



    private fun loadSavedValues() {
        val sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)

        binding.ed0.setText(sharedPreferences.getString("ed0", binding.ed0.text.toString()))
        binding.ed1.setText(sharedPreferences.getString("ed1",  binding.ed1.text.toString()))
        binding.ed2.setText(sharedPreferences.getString("ed2",  binding.ed2.text.toString()))
        binding.ed3.setText(sharedPreferences.getString("ed3",  binding.ed3.text.toString()))
        binding.ed4.setText(sharedPreferences.getString("ed4",  binding.ed4.text.toString()))
        binding.country.setText(sharedPreferences.getString("country",  binding.country.text.toString()))
        binding.phone.setText(sharedPreferences.getString("phone",  binding.phone.text.toString()))
        binding.fax.setText(sharedPreferences.getString("fax",  binding.fax.text.toString()))
        binding.website.setText(sharedPreferences.getString("website",  binding.website.text.toString()))

        binding.direction1.setText(sharedPreferences.getString("direction1",  binding.direction1.text.toString()))
        binding.direction2.setText(sharedPreferences.getString("direction2",  binding.direction2.text.toString()))
        binding.direction3.setText(sharedPreferences.getString("direction3",  binding.direction3.text.toString()))
        binding.direction4.setText(sharedPreferences.getString("direction4",  binding.direction4.text.toString()))
        binding.direction5.setText(sharedPreferences.getString("direction5",  binding.direction5.text.toString()))
        binding.direction6.setText(sharedPreferences.getString("direction6",  binding.direction6.text.toString()))
        binding.direction7.setText(sharedPreferences.getString("direction7",  binding.direction7.text.toString()))
        binding.direction8.setText(sharedPreferences.getString("direction8",  binding.direction8.text.toString()))
    }

    private fun setupAutoSaveListener() {

        binding.ed0.addTextChangedListener {
            saveValue("ed0", binding.ed0.text.toString().trim())
        }
        binding.ed1.addTextChangedListener {
            saveValue("ed1", binding.ed1.text.toString().trim())
        }
        binding.ed2.addTextChangedListener {
            saveValue("ed2", binding.ed2.text.toString().trim())
        }
        binding.ed3.addTextChangedListener {
            saveValue("ed3", binding.ed3.text.toString().trim())
        }
        binding.ed4.addTextChangedListener {
            saveValue("ed4", binding.ed4.text.toString().trim())
        }
        binding.country.addTextChangedListener {
            saveValue("country", binding.country.text.toString().trim())
        }
        binding.phone.addTextChangedListener {
            saveValue("phone", binding.phone.text.toString().trim())
        }
        binding.fax.addTextChangedListener {
            saveValue("fax", binding.fax.text.toString().trim())
        }
        binding.website.addTextChangedListener {
            saveValue("website", binding.website.text.toString().trim())
        }
        binding.direction1.addTextChangedListener {
            saveValue("direction1", binding.direction1.text.toString().trim())
        }
        binding.direction2.addTextChangedListener {
            saveValue("direction2", binding.direction2.text.toString().trim())
        }
        binding.direction3.addTextChangedListener {
            saveValue("direction3", binding.direction3.text.toString().trim())
        }
        binding.direction4.addTextChangedListener {
            saveValue("direction4", binding.direction4.text.toString().trim())
        }
        binding.direction5.addTextChangedListener {
            saveValue("direction5", binding.direction5.text.toString().trim())
        }
        binding.direction6.addTextChangedListener {
            saveValue("direction6", binding.direction6.text.toString().trim())
        }
        binding.direction7.addTextChangedListener {
            saveValue("direction7", binding.direction7.text.toString().trim())
        }
        binding.direction8.addTextChangedListener {
            saveValue("direction8", binding.direction8.text.toString().trim())
        }
    }

    private fun saveValue(key: String, value: String) {
        val sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
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

    private fun getTableFromDataBase(company: String, dataType: String) {
        val tableDao = AppDatabase.getDatabase(applicationContext).pricesDao()

        tableDao.getAllTables(company, dataType).observe(this, Observer { tableList ->
            val validTables = tableList.filterNotNull()

                validTables.forEachIndexed { index, row ->
                    when (index) {
                        0 -> {
                            binding.carType11.text = row.pricecar1.toString().toEditable()
                            binding.carType12.text = row.pricecar2.toString().toEditable()
                            binding.carType13.text = row.pricecar3.toString().toEditable()
                            binding.carType14.text = row.pricecar4.toString().toEditable()
                            binding.carType15.text = row.pricecar5.toString().toEditable()
                            binding.carType16.text = row.pricecar6.toString().toEditable()
                            binding.carType17.text = row.pricecar7.toString().toEditable()
                            binding.carType18.text = row.pricecar8.toString().toEditable()
                        }
                        1 -> {
                            binding.carType21.text = row.pricecar1.toString().toEditable()
                            binding.carType22.text = row.pricecar2.toString().toEditable()
                            binding.carType23.text = row.pricecar3.toString().toEditable()
                            binding.carType24.text = row.pricecar4.toString().toEditable()
                            binding.carType25.text = row.pricecar5.toString().toEditable()
                            binding.carType26.text = row.pricecar6.toString().toEditable()
                            binding.carType27.text = row.pricecar7.toString().toEditable()
                            binding.carType28.text = row.pricecar8.toString().toEditable()
                        }
                        2 -> {
                            binding.carType31.text = row.pricecar1.toString().toEditable()
                            binding.carType32.text = row.pricecar2.toString().toEditable()
                            binding.carType33.text = row.pricecar3.toString().toEditable()
                            binding.carType34.text = row.pricecar4.toString().toEditable()
                            binding.carType35.text = row.pricecar5.toString().toEditable()
                            binding.carType36.text = row.pricecar6.toString().toEditable()
                            binding.carType37.text = row.pricecar7.toString().toEditable()
                            binding.carType38.text = row.pricecar8.toString().toEditable()
                        }
                        3 -> {
                            binding.carType41.text = row.pricecar1.toString().toEditable()
                            binding.carType42.text = row.pricecar2.toString().toEditable()
                            binding.carType43.text = row.pricecar3.toString().toEditable()
                            binding.carType44.text = row.pricecar4.toString().toEditable()
                            binding.carType45.text = row.pricecar5.toString().toEditable()
                            binding.carType46.text = row.pricecar6.toString().toEditable()
                            binding.carType47.text = row.pricecar7.toString().toEditable()
                            binding.carType48.text = row.pricecar8.toString().toEditable()
                        }
                        4 -> {
                            binding.carType51.text = row.pricecar1.toString().toEditable()
                            binding.carType52.text = row.pricecar2.toString().toEditable()
                            binding.carType53.text = row.pricecar3.toString().toEditable()
                            binding.carType54.text = row.pricecar4.toString().toEditable()
                            binding.carType55.text = row.pricecar5.toString().toEditable()
                            binding.carType56.text = row.pricecar6.toString().toEditable()
                            binding.carType57.text = row.pricecar7.toString().toEditable()
                            binding.carType58.text = row.pricecar8.toString().toEditable()
                        }
                        5 -> {
                            binding.carType61.text = row.pricecar1.toString().toEditable()
                            binding.carType62.text = row.pricecar2.toString().toEditable()
                            binding.carType63.text = row.pricecar3.toString().toEditable()
                            binding.carType64.text = row.pricecar4.toString().toEditable()
                            binding.carType65.text = row.pricecar5.toString().toEditable()
                            binding.carType66.text = row.pricecar6.toString().toEditable()
                            binding.carType67.text = row.pricecar7.toString().toEditable()
                            binding.carType68.text = row.pricecar8.toString().toEditable()
                        }
                        6 -> {
                            binding.carType71.text = row.pricecar1.toString().toEditable()
                            binding.carType72.text = row.pricecar2.toString().toEditable()
                            binding.carType73.text = row.pricecar3.toString().toEditable()
                            binding.carType74.text = row.pricecar4.toString().toEditable()
                            binding.carType75.text = row.pricecar5.toString().toEditable()
                            binding.carType76.text = row.pricecar6.toString().toEditable()
                            binding.carType77.text = row.pricecar7.toString().toEditable()
                            binding.carType78.text = row.pricecar8.toString().toEditable()
                        }
                        7 -> {
                            binding.carType81.text = row.pricecar1.toString().toEditable()
                            binding.carType82.text = row.pricecar2.toString().toEditable()
                            binding.carType83.text = row.pricecar3.toString().toEditable()
                            binding.carType84.text = row.pricecar4.toString().toEditable()
                            binding.carType85.text = row.pricecar5.toString().toEditable()
                            binding.carType86.text = row.pricecar6.toString().toEditable()
                            binding.carType87.text = row.pricecar7.toString().toEditable()
                            binding.carType88.text = row.pricecar8.toString().toEditable()
                        }

                    }
                }

        })
    }

    private fun takeScreenshotAndSaveAsPdf() {
        val screenshot = takeScreenshot(binding.root)

        if (screenshot != null) {
            val pdfFile = createPdfFromBitmap(screenshot)
            if (pdfFile != null) {
                showMessage("PDF saved at: ${pdfFile.absolutePath}")
            } else {
                showMessage("Failed to create PDF.")
            }
        } else {
            showMessage("Failed to take screenshot.")
        }
    }

    private fun takeScreenshot(view: View): Bitmap? {
        return try {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            view.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun createPdfFromBitmap(bitmap: Bitmap): File? {
        return try {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(page)

            // Save PDF to external files directory
            val pdfDir = ContextCompat.getExternalFilesDirs(applicationContext, null)[0]
            val pdfFile = File(pdfDir, "screenshot.pdf")

            FileOutputStream(pdfFile).use { output ->
                pdfDocument.writeTo(output)
            }

            pdfDocument.close()
            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}
