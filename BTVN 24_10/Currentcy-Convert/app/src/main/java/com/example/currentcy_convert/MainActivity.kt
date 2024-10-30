package com.example.currentcy_convert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner

    private lateinit var ed1: EditText
    private lateinit var ed2: EditText

    var currencies = arrayOf<String?>(
        "USD",
        "VND",
        "EUR",
        "JPY",
        "CAD",
        "AUD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)

        ed1 = findViewById(R.id.ed1)
        ed2 = findViewById(R.id.ed2)

        spinner1.onItemSelectedListener = this
        spinner2.onItemSelectedListener = this

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            currencies)
        val ad2: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            currencies)

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        ad2.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner1.adapter = ad
        spinner2.adapter = ad2


        ed1.doOnTextChanged { _, _, _, _ ->

            if (ed1.isFocused) {
                val amt = if (ed1.text.isEmpty()) 0.0 else ed1.text.toString().toDouble()
                val convertedCurrency = convertCurrency(
                    amt,
                    spinner1.selectedItem.toString(),
                    spinner2.selectedItem.toString()
                )

                ed2.setText(convertedCurrency.toString())
            }
        }

        ed2.doOnTextChanged { _, _, _, _ ->

            if (ed2.isFocused) {
                val amt = if (ed2.text.isEmpty()) 0.0 else ed2.text.toString().toDouble()
                val convertedCurrency = convertCurrency(
                    amt,
                    spinner2.selectedItem.toString(),
                    spinner1.selectedItem.toString()
                )

                ed1.setText(convertedCurrency.toString())
            }
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id){
            R.id.spinner1 -> {
                val amt = if (ed1.text.isEmpty()) 0.0 else ed1.text.toString().toDouble()
                val convertedCurrency = convertCurrency(
                    amt,
                    spinner1.selectedItem.toString(),
                    spinner2.selectedItem.toString())
                ed2.setText(convertedCurrency.toString())

            }
            R.id.spinner2 -> {
                val amt = if (ed2.text.isEmpty()) 0.0 else ed2.text.toString().toDouble()
                val convertedCurrency = convertCurrency(
                    amt,
                    spinner2.selectedItem.toString(),
                    spinner1.selectedItem.toString())
                ed1.setText(convertedCurrency.toString())

            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun convertCurrency(amt: Double, firstCurrency: String, secondCurrency: String): Double {
        val usdAmount = convertToUSD(amt, firstCurrency)
        return convertFromUSD(usdAmount, secondCurrency)
    }

    private fun convertFromUSD(usdAmount: Double, secondCurrency: String): Double {
        return usdAmount * when (secondCurrency) {
            "USD" -> 1.0
            "EUR" -> 0.92164826
            "VND" -> 25288.0
            "JPY" -> 153.3524
            "CAD" -> 1.3925056
            "AUD" -> 1.5203339
            else -> 0.0
        }
    }

    private fun convertToUSD(amt: Double, firstCurrency: String): Double {
        return amt * when (firstCurrency) {
            "USD" -> 1.0
            "EUR" -> 1.08532
            "VND" -> 0.0000395895
            "JPY" -> 0.00652118
            "CAD" -> 0.718130
            "AUD" -> 0.657845
            else -> 0.0
        }
    }


}