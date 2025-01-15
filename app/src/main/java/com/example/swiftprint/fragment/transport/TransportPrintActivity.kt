package com.example.swiftprint.fragment.transport

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.swiftprint.R
import com.example.swiftprint.databinding.ActivityTransportPrintBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import android.graphics.pdf.PdfDocument
import java.util.*

class TransportPrintActivity : AppCompatActivity() {
    lateinit var binding: ActivityTransportPrintBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransportPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        binding.logo.setOnClickListener {
            takeScreenshotAndSaveAsPDF()
        }
    }

    private fun takeScreenshotAndSaveAsPDF() {
        // Take screenshot of the current screen
        val bitmap = getScreenshot()

        // Convert screenshot to PDF and save it to the device
        saveBitmapAsPDF(bitmap)
    }

    private fun getScreenshot(): Bitmap {
        // Create a Bitmap to capture the view content
        val view = binding.root
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapAsPDF(bitmap: Bitmap) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        // Draw the bitmap to the PDF document page
        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        pdfDocument.finishPage(page)

        // Save the PDF to external storage
        try {
            val pdfDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "SwiftPrint")
            if (!pdfDir.exists()) {
                pdfDir.mkdirs()
            }

            val pdfFile = File(pdfDir, "screenshot_${System.currentTimeMillis()}.pdf")
            val outputStream: OutputStream = FileOutputStream(pdfFile)
            pdfDocument.writeTo(outputStream)
            outputStream.close()

            Toast.makeText(this, "PDF saved to: ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save PDF", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }


private fun initView() {
        val invoiceSubject = intent.getStringExtra("invoiceSubject")
        val companyName = intent.getStringExtra("companyName")
        val employee = intent.getStringExtra("employee")
        val date = intent.getStringExtra("date")
        val companyDetails = intent.getStringExtra("companyDetails")

        val invoiceTail = intent.getStringExtra("invoiceTail")
        val phone = intent.getStringExtra("phone")
        val fax = intent.getStringExtra("fax")
        val website = intent.getStringExtra("website")

        val guest = intent.getStringExtra("guest")
        val totalAmount = intent.getStringExtra("totalAmount")
        val totalAmountNumber = intent.getStringExtra("totalAmountNumber")

        // استقبال البيانات الخاصة بكل InvoiceDetails
        val invoiceDetails1 = intent.getStringExtra("invoiceDetails1")
        val dateInvoice1 = intent.getStringExtra("dateInvoice1")
        val car1 = intent.getStringExtra("car1")
        val rate1 = intent.getStringExtra("rate1")

        val invoiceDetails2 = intent.getStringExtra("invoiceDetails2")
        val dateInvoice2 = intent.getStringExtra("dateInvoice2")
        val car2 = intent.getStringExtra("car2")
        val rate2 = intent.getStringExtra("rate2")

        val invoiceDetails3 = intent.getStringExtra("invoiceDetails3")
        val dateInvoice3 = intent.getStringExtra("dateInvoice3")
        val car3 = intent.getStringExtra("car3")
        val rate3 = intent.getStringExtra("rate3")

        val invoiceDetails4 = intent.getStringExtra("invoiceDetails4")
        val dateInvoice4 = intent.getStringExtra("dateInvoice4")
        val car4 = intent.getStringExtra("car4")
        val rate4 = intent.getStringExtra("rate4")

        val invoiceDetails5 = intent.getStringExtra("invoiceDetails5")
        val dateInvoice5 = intent.getStringExtra("dateInvoice5")
        val car5 = intent.getStringExtra("car5")
        val rate5 = intent.getStringExtra("rate5")

        val invoiceDetails6 = intent.getStringExtra("invoiceDetails6")
        val dateInvoice6 = intent.getStringExtra("dateInvoice6")
        val car6 = intent.getStringExtra("car6")
        val rate6 = intent.getStringExtra("rate6")

        val invoiceDetails7 = intent.getStringExtra("invoiceDetails7")
        val dateInvoice7 = intent.getStringExtra("dateInvoice7")
        val car7 = intent.getStringExtra("car7")
        val rate7 = intent.getStringExtra("rate7")

        val invoiceDetails8 = intent.getStringExtra("invoiceDetails8")
        val dateInvoice8 = intent.getStringExtra("dateInvoice8")
        val car8 = intent.getStringExtra("car8")
        val rate8 = intent.getStringExtra("rate8")

        val invoiceDetails9 = intent.getStringExtra("invoiceDetails9")
        val dateInvoice9 = intent.getStringExtra("dateInvoice9")
        val car9 = intent.getStringExtra("car9")
        val rate9 = intent.getStringExtra("rate9")

        val invoiceDetails10 = intent.getStringExtra("invoiceDetails10")
        val dateInvoice10 = intent.getStringExtra("dateInvoice10")
        val car10 = intent.getStringExtra("car10")
        val rate10 = intent.getStringExtra("rate10")


        binding.dateTv.text=date
        binding.invoiceSubject.text=invoiceSubject
        binding.company.text=companyName
        binding.employee.text=employee
        binding.guest.text=guest
        binding.country.text=invoiceTail
        binding.phone.text=phone
        binding.fax.text=fax
        binding.website.text=website
        binding.totalAmountTvAbc.text=totalAmount
        binding.totalAmountTv.text=totalAmountNumber
        binding.rate1.text=rate1?.trim()
        binding.invince1.text=invoiceDetails1?.trim()
        binding.date1.text=dateInvoice1?.trim()
        binding.car1.text=car1?.trim()

    if (!rate2.isNullOrEmpty() || !invoiceDetails2.isNullOrEmpty() || !dateInvoice2.isNullOrEmpty() || !car2.isNullOrEmpty()) {
        binding.table2.isVisible = true
        binding.table3.isVisible = false
        binding.rate2.text = rate2?.trim()
        binding.invince2.text = invoiceDetails2?.trim()
        binding.date2.text = dateInvoice2?.trim()
        binding.car2.text = car2?.trim()
    } else {
        binding.table2.isVisible = false
        binding.table3.isVisible = false

    }

    if (!rate3.isNullOrEmpty() || !invoiceDetails3.isNullOrEmpty() || !dateInvoice3.isNullOrEmpty() || !car3.isNullOrEmpty()) {
        binding.table3.isVisible = true
        binding.table4.isVisible = false
        binding.rate3.text = rate3?.trim()
        binding.invince3.text = invoiceDetails3?.trim()
        binding.date3.text = dateInvoice3?.trim()
        binding.car3.text = car3?.trim()
    } else {
        binding.table3.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
        binding.table4.isVisible = false
    }

    if (!rate4.isNullOrEmpty() || !invoiceDetails4.isNullOrEmpty() || !dateInvoice4.isNullOrEmpty() || !car4.isNullOrEmpty()) {
        binding.table4.isVisible = true
        binding.table5.isVisible = false
        binding.rate4.text = rate4?.trim()
        binding.invince4.text = invoiceDetails4?.trim()
        binding.date4.text = dateInvoice4?.trim()
        binding.car4.text = car4?.trim()
    } else {
        binding.table4.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
        binding.table5.isVisible = false
    }


    if(!rate5.isNullOrEmpty() || !invoiceDetails5.isNullOrEmpty() || !dateInvoice5.isNullOrEmpty() || !car5.isNullOrEmpty()) {
            binding.table5.isVisible = true
        binding.table6.isVisible = false
        binding.rate5.text = rate5?.trim()
            binding.invince5.text = invoiceDetails5?.trim()
            binding.date5.text = dateInvoice5?.trim()
            binding.car5.text = car5?.trim()
        }
    else {
        binding.table5.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
        binding.table6.isVisible = false
    }

        if(!rate6.isNullOrEmpty() || !invoiceDetails6.isNullOrEmpty() || !dateInvoice6.isNullOrEmpty() || !car6.isNullOrEmpty()) {
            binding.table6.isVisible = true
            binding.table7.isVisible = false
            binding.rate6.text = rate6?.trim()
            binding.invince6.text = invoiceDetails6?.trim()
            binding.date6.text = dateInvoice6?.trim()
            binding.car6.text = car6?.trim()
        }
        else {
            binding.table6.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
            binding.table7.isVisible = false
        }

        if(!rate7.isNullOrEmpty() || !invoiceDetails7.isNullOrEmpty() || !dateInvoice7.isNullOrEmpty() || !car7.isNullOrEmpty()) {
            binding.table7.isVisible = true
            binding.table8.isVisible = false
            binding.rate7.text = rate7?.trim()
            binding.invince7.text = invoiceDetails7?.trim()
            binding.date7.text = dateInvoice7?.trim()
            binding.car7.text = car7?.trim()
        }
        else {
            binding.table7.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
            binding.table8.isVisible = false
        }

        if(!rate8.isNullOrEmpty() || !invoiceDetails8.isNullOrEmpty() || !dateInvoice8.isNullOrEmpty() || !car8.isNullOrEmpty()) {
            binding.table8.isVisible = true
            binding.table9.isVisible = false
            binding.rate8.text = rate8?.trim()
            binding.invince8.text = invoiceDetails8?.trim()
            binding.date8.text = dateInvoice8?.trim()
            binding.car8.text = car8?.trim()
        }
        else {
            binding.table8.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
            binding.table9.isVisible = false
        }

        if(!rate9.isNullOrEmpty() || !invoiceDetails9.isNullOrEmpty() || !dateInvoice9.isNullOrEmpty() || !car9.isNullOrEmpty()) {
            binding.table9.isVisible = true
            binding.table10.isVisible = false
            binding.rate9.text = rate9?.trim()
            binding.invince9.text = invoiceDetails9?.trim()
            binding.date9.text = dateInvoice9?.trim()
            binding.car9.text = car9?.trim()
        }
        else {
            binding.table9.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
            binding.table10.isVisible = false
        }

        if(!rate10.isNullOrEmpty() || !invoiceDetails10.isNullOrEmpty() || !dateInvoice10.isNullOrEmpty() || !car10.isNullOrEmpty()) {
            binding.table10.isVisible = true
            binding.rate10.text = rate10?.trim()
            binding.invince10.text = invoiceDetails10?.trim()
            binding.date10.text = dateInvoice10?.trim()
            binding.car10.text = car10?.trim()
        }
        else {
            binding.table10.isVisible = false // إخفاء الـ table إذا كانت القيم فارغة
        }


    }
}
