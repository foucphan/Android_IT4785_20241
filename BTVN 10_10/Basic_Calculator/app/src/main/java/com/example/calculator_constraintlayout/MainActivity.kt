package com.example.calculator_constraintlayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textResult: TextView

    private var operand1: Int = 0  // Toán hạng thứ nhất (số nguyên)
    private var operand2: Int = 0  // Toán hạng thứ hai (số nguyên)
    private var operator: String = ""   // Phép toán hiện tại
    private var isNewOperation: Boolean = true  // Xác định xem có phải phép tính mới hay không

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.tvResult)

        // Danh sách các ID của các nút số và phép toán
        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide, R.id.btnEqual,
            R.id.btnClear, R.id.btnClearEntry, R.id.btnBackspace, R.id.btnPlusMinus
        )

        // Sử dụng vòng lặp for để gán OnClickListener cho các nút
        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        when (val buttonText = (view as Button).text.toString()) {
            "C" -> clearAll()  // Xóa toàn bộ
            "CE" -> clearEntry()  // Xóa nhập liệu hiện tại
            "BS" -> backspace()  // Xóa ký tự cuối
            "+", "-", "x", "/" -> setOperator(buttonText)  // Thiết lập phép toán
            "=" -> calculateResult()  // Tính toán kết quả
            "+/-" -> toggleSign()  // Đổi dấu số hiện tại
            else -> handleNumberInput(buttonText)  // Nhập số
        }
    }

    // Thêm chữ số vào toán hạng hiện tại
    private fun handleNumberInput(number: String) {
        if (isNewOperation) {
            textResult.text = number
            isNewOperation = false
        } else {
            val currentText = textResult.text.toString()
            textResult.text = if (currentText == "0") number else currentText + number
        }
    }

    // Đổi dấu của số hiện tại
    private fun toggleSign() {
        val currentNumber = textResult.text.toString().toInt()
        textResult.text = (-currentNumber).toString()
    }

    // Thiết lập phép toán
    private fun setOperator(opString: String) {
        operand1 = textResult.text.toString().toInt()
        operator = opString
        isNewOperation = true
    }

    // Xóa toàn bộ dữ liệu
    private fun clearAll() {
        operand1 = 0
        operand2 = 0
        operator = ""
        isNewOperation = true
        textResult.text = "0"
    }

    // Xóa số nhập hiện tại
    private fun clearEntry() {
        textResult.text = "0"
        isNewOperation = true
    }

    // Xóa ký tự cuối cùng
    private fun backspace() {
        val currentText = textResult.text.toString()
        if (currentText.length > 1) {
            textResult.text = currentText.dropLast(1)
        } else {
            textResult.text = "0"
        }
    }

    // Thực hiện phép toán dựa trên toán tử
    @SuppressLint("SetTextI18n")
    private fun calculateResult() {
        operand2 = textResult.text.toString().toInt()

        val result = when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "x" -> operand1 * operand2
            "/" -> if (operand2 != 0) operand1 / operand2 else {
                textResult.text = "Error"
                return
            }
            else -> 0
        }

        textResult.text = result.toString()
        isNewOperation = true
    }
}
