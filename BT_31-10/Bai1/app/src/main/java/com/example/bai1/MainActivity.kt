package com.example.bai1
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bai1.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo các view từ layout
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber) // ID của EditText trong XML
        val radioEven = findViewById<RadioButton>(R.id.radioEven)       // ID của RadioButton "Số chẵn"
        val radioOdd = findViewById<RadioButton>(R.id.radioOdd)         // ID của RadioButton "Số lẻ"
        val radioSquare = findViewById<RadioButton>(R.id.radioSquare)   // ID của RadioButton "Số chính phương"
        val buttonShow = findViewById<Button>(R.id.buttonShow)          // ID của Button "Show"
        val listViewResults = findViewById<ListView>(R.id.listViewResults) // ID của ListView để hiển thị kết quả
        val textViewError = findViewById<TextView>(R.id.textViewError)      // ID của TextView hiển thị lỗi

        buttonShow.setOnClickListener {
            val input = editTextNumber.text.toString()

            // Xóa thông báo lỗi trước đó
            textViewError.text = ""
            listViewResults.adapter = null

            // Kiểm tra dữ liệu đầu vào
            val n = input.toIntOrNull()
            if (n == null || n < 0) {
                textViewError.text = "Vui lòng nhập số nguyên dương hợp lệ!"
                return@setOnClickListener
            }

            // Xác định loại số dựa trên lựa chọn và tạo danh sách kết quả
            val resultList = when {
                radioEven.isChecked -> generateEvenNumbers(n)
                radioOdd.isChecked -> generateOddNumbers(n)
                radioSquare.isChecked -> generateSquareNumbers(n)
                else -> emptyList() // Trường hợp không chọn RadioButton nào
            }

            // Hiển thị kết quả trong ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
            listViewResults.adapter = adapter
        }
    }

    // Hàm sinh các số chẵn từ 0 đến n
    private fun generateEvenNumbers(n: Int): List<Int> {
        return (0..n step 2).toList()
    }

    // Hàm sinh các số lẻ từ 1 đến n
    private fun generateOddNumbers(n: Int): List<Int> {
        return (1..n step 2).toList()
    }

    // Hàm sinh các số chính phương từ 0 đến n
    private fun generateSquareNumbers(n: Int): List<Int> {
        val squareNumbers = mutableListOf<Int>()
        var i = 0
        while (i * i <= n) {
            squareNumbers.add(i * i)
            i++
        }
        return squareNumbers
    }
}
