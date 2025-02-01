package com.example.swiftprint.fragment.transport
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.swiftprint.database.model.AppDatabase
import com.example.swiftprint.databinding.FragmentTransportDataFormBinding
import com.example.swiftprint.fragment.transport.TransportPrintActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TransportDataFormFragment : Fragment() {
    private lateinit var binding: FragmentTransportDataFormBinding
    lateinit var calendar: Calendar
    private val companyNamesCache = mutableListOf<String>()
    private val invoiceDetailsNamesCache = mutableListOf<String>()
    private val invoiceSubjectNamesCache = mutableListOf<String>()
    private val invoiceTailNamesCache = mutableListOf<String>()
    private val sharedPrefKey = "TransportDataFormPreferences"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTransportDataFormBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        loadSavedValues()
        setupAutoSaveListener()

        initializeCompanySpinner()
        initializeInvoiceDetailsSpinners()
        initializeInvoiceSubjectSpinner()
        initializeInvoiceTailSpinner()
        binding.dateTv.setOnClickListener{showDatePicker()}
        binding.dateEnteredTv1.setOnClickListener{showDatePicker2(1)}
        binding.dateEnteredTv2.setOnClickListener{showDatePicker2(2)}
        binding.dateEnteredTv3.setOnClickListener{showDatePicker2(3)}
        binding.dateEnteredTv4.setOnClickListener{showDatePicker2(4)}
        binding.dateEnteredTv5.setOnClickListener{showDatePicker2(5)}
        binding.dateEnteredTv6.setOnClickListener{showDatePicker2(6)}
        binding.dateEnteredTv7.setOnClickListener{showDatePicker2(7)}
        binding.dateEnteredTv8.setOnClickListener{showDatePicker2(8)}
        binding.dateEnteredTv9.setOnClickListener{showDatePicker2(9)}
        binding.dateEnteredTv10.setOnClickListener{showDatePicker2(10)}
        binding.btnPrint.setOnClickListener { NavigateToTransportPrientActivity() }
        binding.addEntered1.setOnClickListener {changeVisibility1()}
        binding.addEntered2.setOnClickListener {changeVisibility2()}
        binding.addEntered3.setOnClickListener {changeVisibility3()}
        binding.addEntered4.setOnClickListener {changeVisibility4()}
        binding.addEntered5.setOnClickListener {changeVisibility5()}
        binding.addEntered6.setOnClickListener {changeVisibility6()}
        binding.addEntered7.setOnClickListener {changeVisibility7()}
        binding.addEntered8.setOnClickListener {changeVisibility8()}
        binding.addEntered9.setOnClickListener {changeVisibility9()}
        binding.addEntered10.setOnClickListener {changeVisibility10()}

    }
    private fun showDatePicker2(textViewNumber: Int) {
        val picker = DatePickerDialog(
            requireActivity(),
            { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                // إعداد التنسيق بالشكل المطلوب
                val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.ENGLISH)
                val formattedDate = dateFormat.format(calendar.time)

                // تعيين التاريخ في الـ TextView المحدد بناءً على الرقم
                when (textViewNumber) {
                    1 -> binding.dateEnteredTv1.text = formattedDate.toEditable()
                    2 -> binding.dateEnteredTv2.text = formattedDate.toEditable()
                    3 -> binding.dateEnteredTv3.text = formattedDate.toEditable()
                    4 -> binding.dateEnteredTv4.text = formattedDate.toEditable()
                    5 -> binding.dateEnteredTv5.text = formattedDate.toEditable()
                    6 -> binding.dateEnteredTv6.text = formattedDate.toEditable()
                    7 -> binding.dateEnteredTv7.text = formattedDate.toEditable()
                    8 -> binding.dateEnteredTv8.text = formattedDate.toEditable()
                    9 -> binding.dateEnteredTv9.text = formattedDate.toEditable()
                    10 -> binding.dateEnteredTv10.text = formattedDate.toEditable()
                    else -> {
                        println("Invalid TextView number")
                    }
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }

    private fun showDatePicker() {
        val picker = DatePickerDialog(
            requireActivity(),
            { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                // إعداد التنسيق بالإنجليزي
                val englishLocale = Locale("en")
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", englishLocale)

                val formattedDate = dateFormat.format(calendar.time)

                binding.dateTv.text = formattedDate.toEditable()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }
    private fun changeVisibility10() {
        binding.linear10.isVisible=true

    }
    private fun changeVisibility9() {
        binding.linear9.isVisible=true
        binding.addEntered10.isVisible=true
    }
    private fun changeVisibility8() {
        binding.linear8.isVisible=true
        binding.addEntered9.isVisible=true
    }
    private fun changeVisibility7() {
        binding.linear7.isVisible=true
        binding.addEntered8.isVisible=true
    }
    private fun changeVisibility6() {
        binding.linear6.isVisible=true
        binding.addEntered7.isVisible=true
    }
    private fun changeVisibility5() {
        binding.linear5.isVisible=true
        binding.addEntered6.isVisible=true
    }
    private fun changeVisibility4() {
        binding.linear4.isVisible=true
        binding.addEntered5.isVisible=true
    }
    private fun changeVisibility3() {
        binding.linear3.isVisible=true
        binding.addEntered4.isVisible=true
    }
    private fun changeVisibility2() {
        binding.linear2.isVisible=true
        binding.addEntered3.isVisible=true
    }
    private fun changeVisibility1() {
        binding.linear1.isVisible=true
        binding.addEntered2.isVisible=true
    }
    private fun initializeCompanySpinner() {
        val companyDao = AppDatabase.getDatabase(requireContext().applicationContext).companyDao()

        companyDao.getAllCompanyNames().observe(viewLifecycleOwner) { companyNames ->
            // تحقق من أن companyNames ليست null
            val names = companyNames ?: emptyList()
            if (names != companyNamesCache) {
                companyNamesCache.clear()
                companyNamesCache.addAll(names)
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    companyNamesCache
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.companySpinner.adapter = adapter
            }

            binding.companySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedCompanyName = companyNamesCache[position].trim()
                    updateCompanyData(selectedCompanyName)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
    private fun initializeEmployeeSpinner(selectedCompanyName: String) {
        val employeeDao = AppDatabase.getDatabase(requireContext().applicationContext).employeeDao()

        employeeDao.getEmployeeByCompanyName(selectedCompanyName).observe(viewLifecycleOwner) { employees ->
            val employeesList = employees?.mapNotNull { it.name } ?: emptyList()
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                employeesList
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.employeeSpinner.adapter = adapter
        }
    }
    private fun initializeInvoiceDetailsSpinners() {
        val invoiceDetailsDao = AppDatabase.getDatabase(requireContext().applicationContext).invoiceDetailsDao()

        invoiceDetailsDao.getAllInvoiceDetails().observe(viewLifecycleOwner) { invoiceDetails ->

            val details = invoiceDetails ?: emptyList()
            val invoiceDetailsNamesList = details.filterNotNull()
            if (invoiceDetailsNamesList != invoiceDetailsNamesCache) {
                invoiceDetailsNamesCache.clear()
                invoiceDetailsNamesCache.add("") // إضافة خيار فارغ في أول القائمة
                invoiceDetailsNamesCache.addAll(invoiceDetailsNamesList)

                // إنشاء Adapter مشترك لكل Spinner
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    invoiceDetailsNamesCache
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val spinners = listOf(
                    binding.invoiceDetailsSpinner1,
                    binding.invoiceDetailsSpinner2,
                    binding.invoiceDetailsSpinner3,
                    binding.invoiceDetailsSpinner4,
                    binding.invoiceDetailsSpinner5,
                    binding.invoiceDetailsSpinner6,
                    binding.invoiceDetailsSpinner7,
                    binding.invoiceDetailsSpinner8,
                    binding.invoiceDetailsSpinner9,
                    binding.invoiceDetailsSpinner10
                )

                // تعيين Adapter لكل Spinner
                spinners.forEach { spinner ->
                    spinner.adapter = adapter
                }

                // إعداد حدث اختيار لكل Spinner
                spinners.forEachIndexed { index, spinner ->
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            // إذا كانت القيمة المختارة هي الفارغة
                            if (position == 0) {
                                println("Spinner $index is empty (no selection)")
                            } else {
                                val selectedDetail = invoiceDetailsNamesCache[position]
                                println("Spinner $index selected value: $selectedDetail")
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                }
            }
        }
    }
    private fun initializeInvoiceSubjectSpinner() {
        val invoiceSubjectsDao = AppDatabase.getDatabase(requireContext().applicationContext).invoiceSubjectDao()

        invoiceSubjectsDao.getAllInvoiceSubject().observe(viewLifecycleOwner) { invoiceSubject ->
            // تحقق من أن invoiceSubject ليست null
            val subjects = invoiceSubject ?: emptyList()
            val invoiceSubjectNamesList = subjects.filterNotNull()
            if (invoiceSubjectNamesList != invoiceSubjectNamesCache) {
                invoiceSubjectNamesCache.clear()
                invoiceSubjectNamesCache.addAll(invoiceSubjectNamesList)


                if (invoiceSubjectNamesCache.size == 1) {
                    binding.invoiceSubjectSpinner.setSelection(0)
                }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    invoiceSubjectNamesCache
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.invoiceSubjectSpinner.adapter = adapter
            }
        }
    }
    private fun initializeInvoiceTailSpinner() {
        val invoiceTailDao = AppDatabase.getDatabase(requireContext().applicationContext).invoiceTailDao()
        invoiceTailDao.getAllInvoiceTail().observe(viewLifecycleOwner) { invoiceTailName ->
            // تحقق من أن invoiceTailName ليست null
            val tails = invoiceTailName ?: emptyList()
            if (tails != invoiceTailNamesCache) {
                invoiceTailNamesCache.clear()
                invoiceTailNamesCache.addAll(tails)
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    invoiceTailNamesCache
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.invoiceTailSpinner.adapter = adapter
            }

            binding.invoiceTailSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedinvoiceTailName = invoiceTailNamesCache[position].trim()
                    updateinvoiceTailData(selectedinvoiceTailName)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
    private fun updateinvoiceTailData(selectedinvoiceTailName: String) {
        val invoiceTailDao = AppDatabase.getDatabase(requireContext().applicationContext).invoiceTailDao()

        invoiceTailDao.getInvoiceTailByCountry(selectedinvoiceTailName).observe(viewLifecycleOwner) { invoiceTail ->

            binding.websiteTail.text = invoiceTail?.website?.toEditable()
            binding.phoneTailNum.text=invoiceTail?.phoneNumber?.toEditable()
            binding.faxTailNum.text=invoiceTail?.faxNumber?.toEditable()
        }
    }
    private fun updateCompanyData(selectedCompanyName: String) {
        getDataOfCompany(selectedCompanyName)
        initializeEmployeeSpinner(selectedCompanyName)
    }
    private fun getDataOfCompany(companyName: String) {
        val companyDao = AppDatabase.getDatabase(requireContext().applicationContext).companyDao()

        companyDao.getAllCompanyByName(companyName).observe(viewLifecycleOwner) { company ->
            val details = company?.details ?: "لم يتم العثور على المعلومات"
            binding.companyDetailsTv.text = details.toEditable()
        }
    }
    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
    private fun NavigateToTransportPrientActivity() {
        val invoiceSubject = binding.invoiceSubjectSpinner.selectedItem?.toString()
        val companyName = binding.companySpinner.selectedItem?.toString()
        val employee = binding.employeeSpinner.selectedItem?.toString()
        val date = binding.dateTv.text.toString()
        val companyDetails = binding.companyDetailsTv.text.toString()

        val invoiceTail = binding.invoiceTailSpinner.selectedItem?.toString()
        val phone = binding.phoneTailNum.text.toString()
        val fax = binding.faxTailNum.text.toString()
        val website = binding.websiteTail.text.toString()

        val guest = binding.guestNameTv.text.toString()
        val totalAmount = binding.totalAmountTv.text.toString()
        val totalAmountNumber = binding.totalAmountNumberTv.text.toString()


        val invoiceDetails1 = binding.invoiceDetailsSpinner1.selectedItem?.toString()
        val dateinvoice1 = binding.dateEnteredTv1.text.toString()
        val car1 = binding.carTypeTv1.text.toString()
        val rate1 = binding.debitTv1.text.toString()

        val invoiceDetails2 = binding.invoiceDetailsSpinner2.selectedItem?.toString()
        val dateinvoice2 = binding.dateEnteredTv2.text.toString()
        val car2= binding.carTypeTv2.text.toString()
        val rate2 = binding.debitTv2.text.toString()

        val invoiceDetails3 = binding.invoiceDetailsSpinner3.selectedItem?.toString()
        val dateinvoice3 = binding.dateEnteredTv3.text.toString()
        val car3 = binding.carTypeTv3.text.toString()
        val rate3 = binding.debitTv3.text.toString()

        val invoiceDetails4 = binding.invoiceDetailsSpinner4.selectedItem?.toString()
        val dateinvoice4 = binding.dateEnteredTv4.text.toString()
        val car4 = binding.carTypeTv4.text.toString()
        val rate4 = binding.debitTv4.text.toString()



        val invoiceDetails5 = binding.invoiceDetailsSpinner5.selectedItem?.toString()
        val dateinvoice5 = binding.dateEnteredTv5.text.toString()
        val car5 = binding.carTypeTv5.text.toString()
        val rate5 = binding.debitTv5.text.toString()

        val invoiceDetails6 = binding.invoiceDetailsSpinner6.selectedItem?.toString()
        val dateinvoice6 = binding.dateEnteredTv6.text.toString()
        val car6 = binding.carTypeTv6.text.toString()
        val rate6 = binding.debitTv6.text.toString()

        val invoiceDetails7 = binding.invoiceDetailsSpinner7.selectedItem?.toString()
        val dateinvoice7 = binding.dateEnteredTv7.text.toString()
        val car7 = binding.carTypeTv7.text.toString()
        val rate7 = binding.debitTv7.text.toString()

        val invoiceDetails8 = binding.invoiceDetailsSpinner8.selectedItem?.toString()
        val dateinvoice8 = binding.dateEnteredTv8.text.toString()
        val car8 = binding.carTypeTv8.text.toString()
        val rate8 = binding.debitTv8.text.toString()

        val invoiceDetails9 = binding.invoiceDetailsSpinner9.selectedItem?.toString()
        val dateinvoice9 = binding.dateEnteredTv9.text.toString()
        val car9 = binding.carTypeTv9.text.toString()
        val rate9 = binding.debitTv9.text.toString()

        val invoiceDetails10 = binding.invoiceDetailsSpinner10.selectedItem?.toString()
        val dateinvoice10 = binding.dateEnteredTv10.text.toString()
        val car10 = binding.carTypeTv10.text.toString()
        val rate10 = binding.debitTv10.text.toString()


        val intent = Intent(requireContext(), TransportPrintActivity::class.java).apply {
            putExtra("invoiceSubject", invoiceSubject)
            putExtra("companyName", companyName)
            putExtra("employee", employee)
            putExtra("date", date)
            putExtra("companyDetails", companyDetails)

            putExtra("invoiceTail", invoiceTail)
            putExtra("phone", phone)
            putExtra("fax", fax)
            putExtra("website", website)

            putExtra("guest", guest)
            putExtra("totalAmount", totalAmount)
            putExtra("totalAmountNumber", totalAmountNumber)

            // إضافة البيانات لكل InvoiceDetails
            putExtra("invoiceDetails1", invoiceDetails1)
            putExtra("dateInvoice1", dateinvoice1)
            putExtra("car1", car1)
            putExtra("rate1", rate1)

            putExtra("invoiceDetails2", invoiceDetails2)
            putExtra("dateInvoice2", dateinvoice2)
            putExtra("car2", car2)
            putExtra("rate2", rate2)

            putExtra("invoiceDetails3", invoiceDetails3)
            putExtra("dateInvoice3", dateinvoice3)
            putExtra("car3", car3)
            putExtra("rate3", rate3)

            putExtra("invoiceDetails4", invoiceDetails4)
            putExtra("dateInvoice4", dateinvoice4)
            putExtra("car4", car4)
            putExtra("rate4", rate4)

            putExtra("invoiceDetails5", invoiceDetails5)
            putExtra("dateInvoice5", dateinvoice5)
            putExtra("car5", car5)
            putExtra("rate5", rate5)

            putExtra("invoiceDetails6", invoiceDetails6)
            putExtra("dateInvoice6", dateinvoice6)
            putExtra("car6", car6)
            putExtra("rate6", rate6)

            putExtra("invoiceDetails7", invoiceDetails7)
            putExtra("dateInvoice7", dateinvoice7)
            putExtra("car7", car7)
            putExtra("rate7", rate7)

            putExtra("invoiceDetails8", invoiceDetails8)
            putExtra("dateInvoice8", dateinvoice8)
            putExtra("car8", car8)
            putExtra("rate8", rate8)

            putExtra("invoiceDetails9", invoiceDetails9)
            putExtra("dateInvoice9", dateinvoice9)
            putExtra("car9", car9)
            putExtra("rate9", rate9)

            putExtra("invoiceDetails10", invoiceDetails10)
            putExtra("dateInvoice10", dateinvoice10)
            putExtra("car10", car10)
            putExtra("rate10", rate10)


        }
        startActivity(intent)
    }

    private fun loadSavedValues() {
        val context = context ?: return
        val sharedPreferences = context.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)

        // العناصر الثابتة
        binding.dateTv.setText(sharedPreferences.getString("dateTv", ""))
        binding.companyDetailsTv.setText(sharedPreferences.getString("companyDetailsTv", ""))
        binding.guestNameTv.setText(sharedPreferences.getString("guestNameTv", ""))
        binding.totalAmountTv.setText(sharedPreferences.getString("totalAmountTv", ""))
        binding.totalAmountNumberTv.setText(sharedPreferences.getString("totalAmountNumberTv", ""))


        for (i in 1..10) {
            when (i) {
                1 -> {
                    binding.carTypeTv1.setText(sharedPreferences.getString("carTypeTv1", ""))
                    binding.dateEnteredTv1.setText(sharedPreferences.getString("dateEnteredTv1", ""))
                    binding.debitTv1.setText(sharedPreferences.getString("debitTv1", ""))
                    setSpinnerSelection(binding.invoiceDetailsSpinner1, "invoiceDetailsSpinner1")
                }
                2 -> {
                    binding.carTypeTv2.setText(sharedPreferences.getString("carTypeTv2", ""))
                    binding.dateEnteredTv2.setText(sharedPreferences.getString("dateEnteredTv2", ""))
                    binding.debitTv2.setText(sharedPreferences.getString("debitTv2", ""))
                    setSpinnerSelection(binding.invoiceDetailsSpinner2, "invoiceDetailsSpinner2")
                }
                3 -> {
                    binding.carTypeTv3.setText(sharedPreferences.getString("carTypeTv3", ""))
                    binding.dateEnteredTv3.setText(sharedPreferences.getString("dateEnteredTv3", ""))
                    binding.debitTv3.setText(sharedPreferences.getString("debitTv3", ""))
                    setSpinnerSelection(binding.invoiceDetailsSpinner3, "invoiceDetailsSpinner3")
                }
                // ... كرر النمط حتى 10
                10 -> {
                    binding.carTypeTv10.setText(sharedPreferences.getString("carTypeTv10", ""))
                    binding.dateEnteredTv10.setText(sharedPreferences.getString("dateEnteredTv10", ""))
                    binding.debitTv10.setText(sharedPreferences.getString("debitTv10", ""))
                    setSpinnerSelection(binding.invoiceDetailsSpinner10, "invoiceDetailsSpinner10")
                }
            }
        }

        // العناصر المنسدلة الرئيسية
        setSpinnerSelection(binding.invoiceSubjectSpinner, "invoiceSubjectSpinner")
        setSpinnerSelection(binding.companySpinner, "companySpinner")
        setSpinnerSelection(binding.employeeSpinner, "employeeSpinner")
        setSpinnerSelection(binding.invoiceTailSpinner, "invoiceTailSpinner")
    }

    private fun setSpinnerSelection(spinner: Spinner, key: String) {
        val sharedPreferences = context?.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        val value = sharedPreferences?.getString(key, null) ?: return
        val adapter = spinner.adapter as? ArrayAdapter<*> ?: return

        // البحث عن العنصر في الـ Adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString() == value) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun setupAutoSaveListener() {
        binding?.apply {
            dateTv?.addTextChangedListener { saveValue("dateTv", it?.toString()?.trim().orEmpty()) }

            companyDetailsTv?.addTextChangedListener { saveValue("companyDetailsTv", it?.toString()?.trim().orEmpty()) }
            guestNameTv?.addTextChangedListener { saveValue("guestNameTv", it?.toString()?.trim().orEmpty()) }
            totalAmountTv?.addTextChangedListener { saveValue("totalAmountTv", it?.toString()?.trim().orEmpty()) }
            totalAmountNumberTv?.addTextChangedListener { saveValue("totalAmountNumberTv", it?.toString()?.trim().orEmpty()) }

            setupSpinnerAutoSave(invoiceSubjectSpinner, "invoiceSubjectSpinner")
            setupSpinnerAutoSave(companySpinner, "companySpinner")
            setupSpinnerAutoSave(employeeSpinner, "employeeSpinner")

            for (i in 1..10) {
                setupAutoSaveForCarFields(i)
            }
        }
    }

    private fun setupAutoSaveForCarFields(index: Int) {
        when (index) {
            1 -> {
                binding.carTypeTv1.addTextChangedListener {
                    saveValue("carTypeTv1", it.toString().trim())
                }
                binding.dateEnteredTv1.addTextChangedListener {
                    saveValue("dateEnteredTv1", it.toString().trim())
                }
                binding.debitTv1.addTextChangedListener {
                    saveValue("debitTv1", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner1, "invoiceDetailsSpinner1")
            }
            2 -> {
                binding.carTypeTv2.addTextChangedListener {
                    saveValue("carTypeTv2", it.toString().trim())
                }
                binding.dateEnteredTv2.addTextChangedListener {
                    saveValue("dateEnteredTv2", it.toString().trim())
                }
                binding.debitTv2.addTextChangedListener {
                    saveValue("debitTv2", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner2, "invoiceDetailsSpinner2")
            }
            3 -> {
                binding.carTypeTv3.addTextChangedListener {
                    saveValue("carTypeTv3", it.toString().trim())
                }
                binding.dateEnteredTv3.addTextChangedListener {
                    saveValue("dateEnteredTv3", it.toString().trim())
                }
                binding.debitTv3.addTextChangedListener {
                    saveValue("debitTv3", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner3, "invoiceDetailsSpinner3")
            }
            4 -> {
                binding.carTypeTv4.addTextChangedListener {
                    saveValue("carTypeTv4", it.toString().trim())
                }
                binding.dateEnteredTv4.addTextChangedListener {
                    saveValue("dateEnteredTv4", it.toString().trim())
                }
                binding.debitTv4.addTextChangedListener {
                    saveValue("debitTv4", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner4, "invoiceDetailsSpinner4")
            }
            5 -> {
                binding.carTypeTv5.addTextChangedListener {
                    saveValue("carTypeTv5", it.toString().trim())
                }
                binding.dateEnteredTv5.addTextChangedListener {
                    saveValue("dateEnteredTv5", it.toString().trim())
                }
                binding.debitTv5.addTextChangedListener {
                    saveValue("debitTv5", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner5, "invoiceDetailsSpinner5")
            }
            6 -> {
                binding.carTypeTv6.addTextChangedListener {
                    saveValue("carTypeTv6", it.toString().trim())
                }
                binding.dateEnteredTv6.addTextChangedListener {
                    saveValue("dateEnteredTv6", it.toString().trim())
                }
                binding.debitTv6.addTextChangedListener {
                    saveValue("debitTv6", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner6, "invoiceDetailsSpinner6")
            }
            7 -> {
                binding.carTypeTv7.addTextChangedListener {
                    saveValue("carTypeTv7", it.toString().trim())
                }
                binding.dateEnteredTv7.addTextChangedListener {
                    saveValue("dateEnteredTv7", it.toString().trim())
                }
                binding.debitTv7.addTextChangedListener {
                    saveValue("debitTv7", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner7, "invoiceDetailsSpinner7")
            }
            8 -> {
                binding.carTypeTv8.addTextChangedListener {
                    saveValue("carTypeTv8", it.toString().trim())
                }
                binding.dateEnteredTv8.addTextChangedListener {
                    saveValue("dateEnteredTv8", it.toString().trim())
                }
                binding.debitTv8.addTextChangedListener {
                    saveValue("debitTv8", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner8, "invoiceDetailsSpinner8")
            }
            9 -> {
                binding.carTypeTv9.addTextChangedListener {
                    saveValue("carTypeTv9", it.toString().trim())
                }
                binding.dateEnteredTv9.addTextChangedListener {
                    saveValue("dateEnteredTv9", it.toString().trim())
                }
                binding.debitTv9.addTextChangedListener {
                    saveValue("debitTv9", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner9, "invoiceDetailsSpinner9")
            }
            10 -> {
                binding.carTypeTv10.addTextChangedListener {
                    saveValue("carTypeTv10", it.toString().trim())
                }
                binding.dateEnteredTv10.addTextChangedListener {
                    saveValue("dateEnteredTv10", it.toString().trim())
                }
                binding.debitTv10.addTextChangedListener {
                    saveValue("debitTv10", it.toString().trim())
                }
                setupSpinnerAutoSave(binding.invoiceDetailsSpinner10, "invoiceDetailsSpinner10")
            }
        }
    }

    private fun setupSpinnerAutoSave(spinner: Spinner?, key: String) {
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position)?.toString() ?: return
                saveValue(key, selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun saveValue(key: String, value: String) {
        val sharedPreferences = context?.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }
}
