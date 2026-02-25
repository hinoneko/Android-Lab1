package com.example.lab1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editPrev = findViewById<EditText>(R.id.edit_prev_value)
        val editCurrent = findViewById<EditText>(R.id.edit_current_value)
        val radioGroup = findViewById<RadioGroup>(R.id.rg_tariffs)
        val btnCalc = findViewById<Button>(R.id.btn_calculate_bill)
        val resultView = findViewById<TextView>(R.id.tv_bill_result)

        btnCalc.setOnClickListener {
            val prevStr = editPrev.text.toString()
            val currStr = editCurrent.text.toString()
            val selectedId = radioGroup.checkedRadioButtonId

            if (prevStr.isEmpty() || currStr.isEmpty() || selectedId == -1) {
                Toast.makeText(this, "Помилка: введіть показники та оберіть тариф!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prevValue = prevStr.toDouble()
            val currValue = currStr.toDouble()

            if (currValue < prevValue) {
                Toast.makeText(this, "Помилка: поточні показники менші за попередні!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rate = when (selectedId) {
                R.id.rb_electric -> 4.32
                R.id.rb_water -> 30.384
                R.id.rb_gas -> 7.96
                else -> 0.0
            }

            val consumed = currValue - prevValue
            val total = consumed * rate

            val serviceName = findViewById<RadioButton>(selectedId).text

            val formattedConsumed = String.format("%.2f", consumed)
            val formattedTotal = String.format("%.2f", total)

            resultView.text = """
                Послуга:
                $serviceName
                
                Використано: $formattedConsumed од.
                
                Разом до сплати: $formattedTotal грн
                """.trimIndent()
        }
    }
}