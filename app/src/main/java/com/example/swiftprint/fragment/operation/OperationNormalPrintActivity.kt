package com.example.swiftprint.fragment.operation

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.swiftprint.databinding.ActivityOperationNormalPrintBinding
import java.io.IOException

class OperationNormalPrintActivity : AppCompatActivity() {
    lateinit var binding: ActivityOperationNormalPrintBinding
    private val sharedPrefKey = "OperationPreferences"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperationNormalPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSavedValues()
        setupAutoSaveListener()

        initView()
    }
    private fun loadSavedValues() {
        val sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        binding.hagezTv.setText(sharedPreferences.getString("hagezTv",  binding.hagezTv.text.toString()))
        binding.dateTv.setText(sharedPreferences.getString("dateTv",  binding.dateTv.text.toString()))
        binding.dayTv.setText(sharedPreferences.getString("dayTv",  binding.dayTv.text.toString()))
        binding.driverNameTv.setText(sharedPreferences.getString("driverNameTv",  binding.driverNameTv.text.toString()))
        binding.driverIdTv.setText(sharedPreferences.getString("driverIdTv",  binding.driverIdTv.text.toString()))
        binding.phoneNumberTv.setText(sharedPreferences.getString("phoneNumberTv",  binding.phoneNumberTv.text.toString()))
        binding.carTypeTv.setText(sharedPreferences.getString("carTypeTv",  binding.carTypeTv.text.toString()))
        binding.boardNumTv.setText(sharedPreferences.getString("boardNumTv",  binding.boardNumTv.text.toString()))

        binding.clientNumTv.setText(sharedPreferences.getString("clientNumTv",  binding.clientNumTv.text.toString()))
        binding.clientNameTv.setText(sharedPreferences.getString("clientNameTv",  binding.clientNameTv.text.toString()))
        binding.ArrivalTimeTv.setText(sharedPreferences.getString("ArrivalTimeTv",  binding.ArrivalTimeTv.text.toString()))
        binding.tripNumTv.setText(sharedPreferences.getString("tripNumTv",  binding.tripNumTv.text.toString()))
        binding.hallTv.setText(sharedPreferences.getString("hallTv",  binding.hallTv.text.toString()))
        binding.startTripTv.setText(sharedPreferences.getString("startTripTv",  binding.startTripTv.text.toString()))
        binding.endTrip.setText(sharedPreferences.getString("endTrip",  binding.endTrip.text.toString()))
    }

    private fun setupAutoSaveListener() {
        binding.hagezTv.addTextChangedListener { saveValue("hagezTv", it.toString().trim()) }
        binding.dateTv.addTextChangedListener { saveValue("dateTv", it.toString().trim()) }
        binding.dayTv.addTextChangedListener { saveValue("dayTv", it.toString().trim()) }
        binding.driverNameTv.addTextChangedListener { saveValue("driverNameTv", it.toString().trim()) }
        binding.driverIdTv.addTextChangedListener { saveValue("driverIdTv", it.toString().trim()) }
        binding.phoneNumberTv.addTextChangedListener { saveValue("phoneNumberTv", it.toString().trim()) }
        binding.carTypeTv.addTextChangedListener { saveValue("carTypeTv", it.toString().trim()) }
        binding.boardNumTv.addTextChangedListener { saveValue("boardNumTv", it.toString().trim()) }
        binding.clientNumTv.addTextChangedListener { saveValue("clientNumTv", it.toString().trim()) }
        binding.clientNameTv.addTextChangedListener { saveValue("clientNameTv", it.toString().trim()) }
        binding.ArrivalTimeTv.addTextChangedListener { saveValue("ArrivalTimeTv", it.toString().trim()) }
        binding.tripNumTv.addTextChangedListener { saveValue("tripNumTv", it.toString().trim()) }
        binding.hallTv.addTextChangedListener { saveValue("hallTv", it.toString().trim()) }
        binding.startTripTv.addTextChangedListener { saveValue("startTripTv", it.toString().trim()) }
        binding.endTrip.addTextChangedListener { saveValue("endTrip", it.toString().trim()) }
    }

    private fun saveValue(key: String, value: String) {
        val sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
    private fun initView() {
        binding.logo.setOnClickListener {
            val rootView = findViewById<View>(android.R.id.content)
            val bitmap = captureScreen(rootView)
            saveBitmapAsPdfWithMediaStore(bitmap, this)
        }

        val selectedCarName = intent.getStringExtra("car_name")
        val driverPhone = intent.getStringExtra("driver_Phone")
        val driverId = intent.getStringExtra("driver_Id")
        val selectedDriverName = intent.getStringExtra("driver_name")
        val carModel = intent.getStringExtra("car_model")
        val clientName = intent.getStringExtra("client_Name")
        val hagezNumber = intent.getStringExtra("hagez_Number")
        val day = intent.getStringExtra("day")
        val date = intent.getStringExtra("date")
        val startTrip = intent.getStringExtra("start_Trip")
        val endTrip = intent.getStringExtra("end_Trip")
        val clientPhone = intent.getStringExtra("client_Phone")
        val tripNumber = intent.getStringExtra("trip_Number")
        val arrivalTime = intent.getStringExtra("arrival_Time")
        val arrivalHall = intent.getStringExtra("arriva_lHall")

        binding.hagezTv.text = hagezNumber
        binding.dateTv.text = "$date م "
        binding.dayTv.text = day
        binding.driverNameTv.text = selectedDriverName
        binding.driverIdTv.text = driverId
        binding.phoneNumberTv.text = driverPhone
        binding.carTypeTv.text = selectedCarName
        binding.boardNumTv.text = carModel
        binding.clientNameTv.text = " العميل :${clientName}"
        binding.clientNumTv.text = "رقم العميل : ${clientPhone}"
        binding.tripNumTv.text = tripNumber
        binding.hallTv.text = arrivalHall
        binding.driverNameTv.text = selectedDriverName
        binding.ArrivalTimeTv.text = arrivalTime
        binding.startTripTv.text = "من: ${startTrip} "
        binding.endTrip.text = "الي : ${endTrip}"
    }

    fun captureScreen(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun saveBitmapAsPdfWithMediaStore(bitmap: Bitmap, context: Context) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        pdfDocument.finishPage(page)

        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "screenshot.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        if (uri != null) {
            try {
                resolver.openOutputStream(uri)?.use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                    Toast.makeText(context, "تم حفظ الصورة كـ PDF في Documents", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "حدث خطأ أثناء الحفظ", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "فشل في إنشاء الملف", Toast.LENGTH_SHORT).show()
        }

        pdfDocument.close()
    }
}