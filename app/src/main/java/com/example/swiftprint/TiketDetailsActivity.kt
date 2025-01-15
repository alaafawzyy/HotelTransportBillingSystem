package com.example.swiftprint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.swiftprint.databinding.ActivityTiketDetailsBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class TiketDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTiketDetailsBinding

    // تعريف طلب الصلاحية
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            val rootView = findViewById<View>(android.R.id.content)
            val bitmap = captureScreen(rootView)
            saveBitmapAsPdf(bitmap, this)
        } else {
            Toast.makeText(this, "الصلاحيات مطلوبة لحفظ الملف", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTiketDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedHotel = intent.getStringExtra("selected_hotel") ?: "غير محدد"
        val goDate = intent.getStringExtra("go_date") ?: "غير محدد"
        val backDate = intent.getStringExtra("back_date") ?: "غير محدد"
        val tiketCounter = intent.getStringExtra("tiketCounter") ?: "غير محدد"

        initView()

        binding.hotelName.text = selectedHotel
        binding.code.text = tiketCounter

        if (backDate != "غير محدد") {
            try {
                val inputFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                val dateback = inputFormat.parse(backDate)
                val datego = inputFormat.parse(goDate)

                val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("ar"))
                val formattedDateback = outputFormat.format(dateback!!)
                val formattedDatego = outputFormat.format(datego!!)

                binding.dateBack.text = formattedDateback
                binding.dateGo.text = formattedDatego
            } catch (e: Exception) {
                binding.dateBack.text = "تاريخ غير صالح"
                binding.dateGo.text = "تاريخ غير صالح"
            }
        } else {
            binding.dateBack.text = backDate
            binding.dateGo.text = goDate
        }
    }

    private fun initView() {
        val randomNumber = (1000..9999).random()
        binding.password.text = randomNumber.toString()
        binding.print.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && // طلب الصلاحية فقط لأجهزة Android 9 وأقل
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            // في الأنظمة الحديثة (Android 10 وأعلى) لا تحتاج لصلاحية WRITE_EXTERNAL_STORAGE
            val rootView = findViewById<View>(android.R.id.content)
            val bitmap = captureScreen(rootView)
            saveBitmapAsPdf(bitmap, this)
        }
    }


    fun captureScreen(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun saveBitmapAsPdf(bitmap: Bitmap, context: Context) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        pdfDocument.finishPage(page)

        // التحقق من إصدار الـ Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // لو الإصدار أعلى من Q (API 29)، استخدم MediaStore
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "screenshot.pdf")
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS) // المسار المتاح في Scoped Storage
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
        } else {
            // لو الإصدار أقل من Q، استخدم getExternalFilesDir لحفظ الملف
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "screenshot.pdf")
            try {
                file.parentFile?.mkdirs()
                val outputStream = FileOutputStream(file)
                pdfDocument.writeTo(outputStream)
                outputStream.close()
                Toast.makeText(context, "تم حفظ الملف في: ${file.absolutePath}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "حدث خطأ أثناء الحفظ", Toast.LENGTH_SHORT).show()
            }
        }

        pdfDocument.close()
    }
}
