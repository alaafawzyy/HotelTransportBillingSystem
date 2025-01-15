package com.example.swiftprint.fragment.transport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.swiftprint.R
import com.example.swiftprint.database.model.AppDatabase
import com.example.swiftprint.database.model.operation.Vehicle
import com.example.swiftprint.database.model.transport.Company
import com.example.swiftprint.database.model.transport.Employee
import com.example.swiftprint.database.model.transport.InvoiceDetails
import com.example.swiftprint.database.model.transport.InvoiceSubject
import com.example.swiftprint.database.model.transport.InvoiceTail
import com.example.swiftprint.database.model.transport.Transportcar
import com.example.swiftprint.databinding.FragmentTransportInsertDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TransportInsertDataFragment : Fragment() {
   private lateinit var binding :FragmentTransportInsertDataBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding=  FragmentTransportInsertDataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnemployeeSave.setOnClickListener { insertEmployeeToDataBase() }
        binding.btnCompanySave.setOnClickListener { insertCompanyToDataBase() }

        binding.btninvoiceSubjectSave.setOnClickListener { insertinvoiceSubjectToDataBase() }
        binding.btninvoiceDetailsSave.setOnClickListener { insertinvoiceDetailsToDataBase() }
        binding.btninvoicetailSave.setOnClickListener { insertinvoicetailToDataBase() }


        binding.btninvoiceDetailsdelete.setOnClickListener {   deleteinvoiceDetailsToDataBase()}
        binding.btninvoiceSubjectdelete.setOnClickListener {   deleteinvoiceSubjectToDataBase()}
        binding.btninvoicetaildelete.setOnClickListener {   deleteinvoicetailToDataBase()}
        binding.btnCompanydelete.setOnClickListener {   deleteCompanyToDataBase()}
        binding.btnemployeedelete.setOnClickListener {   deleteemployeeToDataBase()}
    }

    private fun deleteinvoicetailToDataBase() {
        val country = binding.invoiceCountryEd.text.toString().trim()

        val countryDataDao = AppDatabase.getDatabase(requireContext().applicationContext).invoiceTailDao()

        CoroutineScope(Dispatchers.IO).launch {
            countryDataDao.deleteInvoiceTailByCountryName(country)
        }
        binding.invoiceCountryEd.text?.clear()
        Toast.makeText(requireContext(), "تم حذف  ذيل فاتورة بنجاح", Toast.LENGTH_SHORT).show()
    }

    private fun deleteCompanyToDataBase() {
        val company = binding.companyNameEd.text.toString().trim()

        val companyDataDao = AppDatabase.getDatabase(requireContext().applicationContext).companyDao()

        CoroutineScope(Dispatchers.IO).launch {
            companyDataDao.deleteCompanyByName(company)
        }
        binding.companyNameEd.text?.clear()
        Toast.makeText(requireContext(), "تم حذف  الشركة بنجاح", Toast.LENGTH_SHORT).show()
    }

    private fun deleteemployeeToDataBase() {
        val employee = binding.employeeNameEd.text.toString().trim()

        val employeeDataDao = AppDatabase.getDatabase(requireContext().applicationContext).employeeDao()

        CoroutineScope(Dispatchers.IO).launch {
            employeeDataDao.deleteEmployeeByName(employee)
        }
        binding.employeeNameEd.text?.clear()
        Toast.makeText(requireContext(), "تم حذف  الموظف بنجاح", Toast.LENGTH_SHORT).show()
    }

    private fun deleteinvoiceDetailsToDataBase() {
        val invoiceDetails = binding.invoiceDetailsEd.text.toString().trim()

        val invoiceDetailsDataDao = AppDatabase.getDatabase(requireContext().applicationContext).invoiceDetailsDao()

        CoroutineScope(Dispatchers.IO).launch {
            invoiceDetailsDataDao.deleteInvoiceDetails(invoiceDetails)
        }
        binding.invoiceDetailsEd.text?.clear()
        Toast.makeText(requireContext(), "تم حذف تفاصيل فاتورة بنجاح", Toast.LENGTH_SHORT).show()
    }


    private fun deleteinvoiceSubjectToDataBase() {
        val invoiceSubject = binding.invoiceSubjectEd.text.toString().trim()

        val invoiceSubjectDataDao = AppDatabase.getDatabase(requireContext().applicationContext).invoiceSubjectDao()

        CoroutineScope(Dispatchers.IO).launch {
            invoiceSubjectDataDao.deleteInvoiceSubjectByName(invoiceSubject)
        }
        binding.invoiceSubjectEd.text?.clear()
        Toast.makeText(requireContext(), "تم حذف موضوع فاتورة بنجاح", Toast.LENGTH_SHORT).show()
    }

    private fun insertinvoicetailToDataBase() {
        val country = binding.invoiceCountryEd.text.toString().trim()
        val phone = binding.invoicePhoneEd.text.toString().trim()
        val fax = binding.invoiceFaxEd.text.toString().trim()
        val websity = binding.invoiceWebsityEd.text.toString().trim()
        val  invoiceTail= InvoiceTail(id = null, country = country.takeIf { it.isNotEmpty() },
            phoneNumber = phone.takeIf { it.isNotEmpty() },
            faxNumber = fax.takeIf { it.isNotEmpty() },
            website = websity.takeIf { it.isNotEmpty() },
        )
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                AppDatabase.getDatabase(requireContext().applicationContext)
                    .invoiceTailDao()
                    .insertInvoiceTail(invoiceTail)
                Toast.makeText(requireContext(), "تم حفظ ذيل فاتورة بنجاح", Toast.LENGTH_SHORT).show()

                binding.invoiceCountryEd.text?.clear()
                binding.invoicePhoneEd.text?.clear()
                binding.invoiceFaxEd.text?.clear()
                binding.invoiceWebsityEd.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "حدث خطأ أثناء الحفظ: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertinvoiceDetailsToDataBase() {
        val details = binding.invoiceDetailsEd.text.toString().trim()
        val  invoiceDetails= InvoiceDetails(id = null, invoiceDetails = details.takeIf { it.isNotEmpty() }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                AppDatabase.getDatabase(requireContext().applicationContext)
                    .invoiceDetailsDao()
                    .insertInvoiceDetails(invoiceDetails)
                Toast.makeText(requireContext(), "تم حفظ تفاصيل فاتورة بنجاح", Toast.LENGTH_SHORT).show()

                binding.invoiceDetailsEd.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "حدث خطأ أثناء الحفظ: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertinvoiceSubjectToDataBase() {
        val subject = binding.invoiceSubjectEd.text.toString().trim()
        val  invoiceSubject= InvoiceSubject(id = null, invoiceSubject = subject.takeIf { it.isNotEmpty() }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                AppDatabase.getDatabase(requireContext().applicationContext)
                    .invoiceSubjectDao()
                    .insertInvoiceSubject(invoiceSubject)
                Toast.makeText(requireContext(), "تم حفظ موضوع فاتورة بنجاح", Toast.LENGTH_SHORT).show()

                binding.invoiceSubjectEd.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "حدث خطأ أثناء الحفظ: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun insertCompanyToDataBase() {
        val companyDetails = binding.companyDetailsEd.text.toString().trim()
        val companyName = binding.companyNameEd.text.toString().trim()
        val company = Company(id = null, name = companyName.takeIf { it.isNotEmpty() }
            , details = companyDetails.takeIf { it.isNotEmpty() }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                AppDatabase.getDatabase(requireContext().applicationContext)
                    .companyDao()
                    .insertCompany(company)
                Toast.makeText(requireContext(), "تم حفظ الشركة بنجاح", Toast.LENGTH_SHORT).show()

                binding.companyDetailsEd.text?.clear()
                binding.companyNameEd.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "حدث خطأ أثناء الحفظ: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertEmployeeToDataBase() {
        val employeeName = binding.employeeNameEd.text.toString().trim()
        val employeeCompany = binding.emplyeeCompanyNameEd.text.toString().trim()
        val employee = Employee(id = null, name = employeeName.takeIf { it.isNotEmpty() }
            , companyName = employeeCompany.takeIf { it.isNotEmpty() }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                AppDatabase.getDatabase(requireContext().applicationContext)
                    .employeeDao()
                    .insertEmployee(employee)
                Toast.makeText(requireContext(), "تم حفظ الموظف بنجاح", Toast.LENGTH_SHORT).show()

                binding.employeeNameEd.text?.clear()
                binding.emplyeeCompanyNameEd.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "حدث خطأ أثناء الحفظ: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }


