package com.example.swiftprint.fragment.operation

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.swiftprint.databinding.ActivityOperationContractPrintBinding
import android.graphics.pdf.PdfDocument
import java.io.File
import java.io.FileOutputStream
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.IOException

class OperationContractPrintActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOperationContractPrintBinding

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            captureAndSavePdf()
        } else {
            Toast.makeText(this, "الإذن مرفوض! يجب منح الإذن لحفظ الـ PDF", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperationContractPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                captureAndSavePdf()
            }
        } else {
            captureAndSavePdf()
        }
    }

    private fun capturePage(view: android.view.View): Bitmap {
        val width = view.width
        val height = view.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun captureAndSavePdf() {
        binding.root.post {
            val page1Bitmap = capturePage(binding.firstSection)
            val page2Bitmap = capturePage(binding.secondSection)

            try {
                val pdfFile = createPdf(listOf(page1Bitmap, page2Bitmap))
                if (pdfFile != null) {
                    Toast.makeText(this, "تم إنشاء PDF: ${pdfFile.absolutePath}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "فشل في إنشاء PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createPdf(images: List<Bitmap>): File? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use MediaStore for Android 10 (API 29) and above
            val resolver = contentResolver
            val contentValues = android.content.ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "contract_print.pdf")
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS) // Scoped Storage
            }

            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
            if (uri != null) {
                try {
                    resolver.openOutputStream(uri)?.let { outputStream ->
                        writePdfToStream(images, outputStream as FileOutputStream)
                    }
                    // Open FileDescriptor to get the path properly on Android 10 and above
                    val descriptor = resolver.openFileDescriptor(uri, "w")
                    val pdfFile = File(descriptor?.fileDescriptor?.toString())
                    return pdfFile
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "حدث خطأ أثناء الحفظ", Toast.LENGTH_SHORT).show()
                    null
                }
            } else {
                Toast.makeText(this, "فشل في إنشاء الملف", Toast.LENGTH_SHORT).show()
                null
            }
        } else {
            // For devices below Android 10, use old approach with WRITE_EXTERNAL_STORAGE
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val pdfFile = File(directory, "contract_print.pdf")
            try {
                FileOutputStream(pdfFile).use { outputStream ->
                    writePdfToStream(images, outputStream)
                }
                return pdfFile
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "فشل في حفظ الملف", Toast.LENGTH_SHORT).show()
                null
            }
        }
    }

    private fun writePdfToStream(images: List<Bitmap>, outputStream: FileOutputStream) {
        val document = PdfDocument()
        images.forEach { bitmap ->
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = document.startPage(pageInfo)

            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            document.finishPage(page)
        }
        document.writeTo(outputStream)
        document.close()
    }

private fun initView() {
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

        val personOneName = intent.getStringExtra("personOneName")
        val personOneNationality = intent.getStringExtra("personOneNationality")
        val personOneid = intent.getStringExtra("personOneid")

        val personTwoName = intent.getStringExtra("personTwoName")
        val personTwoNationality = intent.getStringExtra("personTwoNationality")
        val personTwoid = intent.getStringExtra("personTwoid")

        val personThreeName = intent.getStringExtra("personThreeName")
        val personThreeNationality = intent.getStringExtra("personThreeNationality")
        val personThreeid = intent.getStringExtra("personThreeid")


        val personFourName = intent.getStringExtra("personFourName")
        val personFourNationality = intent.getStringExtra("personFourNationality")
        val personFourid = intent.getStringExtra("personFourid")

        val personFiveName = intent.getStringExtra("personFiveName")
        val personFiveNationality = intent.getStringExtra("personFiveNationality")
        val personFiveid = intent.getStringExtra("personFiveid")

        val personSixName = intent.getStringExtra("personSixName")
        val personSixNationality = intent.getStringExtra("personSixNationality")
        val personSixid = intent.getStringExtra("personSixid")

        val personSevenName = intent.getStringExtra("personSevenName")
        val personSevenNationality = intent.getStringExtra("personSevenNationality")
        val personSevenid = intent.getStringExtra("personSevenid")

        val personEightName = intent.getStringExtra("personEightName")
        val personEightNationality = intent.getStringExtra("personEightNationality")
        val personEightid = intent.getStringExtra("personEightid")

        val personNineName = intent.getStringExtra("personNineName")
        val personNineNationality = intent.getStringExtra("personNineNationality")
        val personNineid = intent.getStringExtra("personNineid")

        val personTenName = intent.getStringExtra("personTenName")
        val personTenNationality = intent.getStringExtra("personTenNationality")
        val personTenid = intent.getStringExtra("personTenid")

        val clientNationality = intent.getStringExtra("clientNationality")
        val ClientInNumberNumber = intent.getStringExtra("ClientInNumberNumber")
        val tripPrice = intent.getStringExtra("tripPrice")
        val tripDuration = intent.getStringExtra("tripDuration")
    val clientId_num=intent.getStringExtra("clientId_num")

        ////////////////////////////////////////////////////////////////////////////////////////

          binding.date1Tv.text=date

          binding.clientName.text=clientName
          binding.start1Trip.text=startTrip
    binding.end1Trip.text=endTrip
    binding.tripDuration.text=tripDuration
    binding.tripPrice.text=tripPrice

    binding.dateTv.text="التاريخ:$date م "
    binding.tripTimeTv.text="وقت الرحلة: $arrivalTime"
    binding.dayTv.text="اليوم:$day"

    binding.startTrip.text=" من:$startTrip"
    binding.endTrip.text=" الى:$endTrip"
    binding.tripNumbTv.text="رقم الرحلة: $tripNumber"
    binding.hagezNumTv.text="رقم الحجز : $hagezNumber"

    binding.driverNameTv.text=selectedDriverName
    binding.idNumberTv.text=driverId
    binding.driverPhoneTv.text=driverPhone
    binding.carTypeTv.text=selectedCarName
    binding.boardNumTv.text=carModel


    binding.clientNameTv.text=clientName
    binding.clientNationalityTv.text=clientNationality
    binding.phoneNumTv.text=clientPhone
    binding.idClientNumberTv.text=clientId_num

    binding.personIdNum1.text=personOneName
    binding.personNationality1.text=personOneNationality
    binding.personId1.text=personOneid

    binding.personIdNum2.text=personTwoName
    binding.personNationality2.text=personTwoNationality
    binding.personId2.text=personTwoid

    binding.personIdNum3.text=personThreeName
    binding.personNationality3.text=personThreeNationality
    binding.personId3.text=personThreeid

    binding.personIdNum4.text=personFourName
    binding.personNationality4.text=personFourNationality
    binding.personId4.text=personFourid

    binding.personIdNum5.text=personFiveName
    binding.personNationality5.text=personFiveNationality
    binding.personId5.text=personFiveid

    binding.personIdNum6.text=personSixName
    binding.personNationality6.text=personSixNationality
    binding.personId6.text=personSixid

    binding.personIdNum7.text=personSevenName
    binding.personNationality7.text=personSevenNationality
    binding.personId7.text=personSevenid

    binding.personIdNum8.text=personEightName
    binding.personNationality8.text=personEightNationality
    binding.personId8.text=personEightid

    binding.personIdNum9.text=personNineName
    binding.personNationality9.text=personNineNationality
    binding.personId9.text=personNineid

    binding.personIdNum10.text=personTenName
    binding.personNationality10.text=personTenNationality
    binding.personId10.text=personTenid


    }
}


