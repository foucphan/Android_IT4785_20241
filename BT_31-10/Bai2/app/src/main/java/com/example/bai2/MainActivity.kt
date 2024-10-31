package com.example.bai2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bai2.Student
import com.example.bai2.StudentAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: StudentAdapter
    private var studentList: MutableList<Student> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ EditText và RecyclerView
        val searchInput: EditText = findViewById(R.id.search_input)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        studentList.add(Student("Nguyen Van A", "123456"))
        studentList.add(Student("Tran Thi B", "654321"))
        studentList.add(Student("Tran Thi B1", "6543210"))
        studentList.add(Student("Tran Thi D", "65432111"))
        studentList.add(Student("Tran Thi E", "6543DS21"))
        studentList.add(Student("Tran Thi C", "654FG321"))
        studentList.add(Student("Tran Thi F", "654SA321"))
        // Thêm nhiều sinh viên khác...

        adapter = StudentAdapter(studentList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Tìm kiếm
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}