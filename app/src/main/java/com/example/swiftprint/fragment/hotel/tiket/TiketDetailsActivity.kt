package com.example.swiftprint.fragment.hotel.tiket


import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.swiftprint.databinding.ActivityTiketDetailsBinding
import java.io.File
import java.io.FileOutputStream

class TiketDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTiketDetailsBinding
    lateinit var viewModel: TiketDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTiketDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TiketDetailsViewModel::class.java)

        val selectedHotel = intent.getStringExtra("selected_hotel") ?: "غير محدد"
        val goDate = intent.getStringExtra("go_date") ?: "غير محدد"
        val backDate = intent.getStringExtra("back_date") ?: "غير محدد"
        val tiketCounter = intent.getStringExtra("tiketCounter") ?: "غير محدد"

        initView()

        binding.hotelName.text = selectedHotel
        binding.code.text = tiketCounter

        if (backDate != "غير محدد") {
            binding.dateBack.text = viewModel.formatDate(backDate)
            binding.dateGo.text = viewModel.formatDate(goDate)
        } else {
            binding.dateBack.text = backDate
            binding.dateGo.text = goDate
        }
    }

    private fun initView() {
        binding.password.text = viewModel.generateRandomPassword()
        binding.print.setOnClickListener {
            takeScreenshotAndSaveAsPdf()
        }
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
}
