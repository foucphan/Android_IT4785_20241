package com.ldh.sdcard_list

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity(), FolderClickListener {

    private val folders = ArrayList<Folder>()
    private lateinit var recyclerView: RecyclerView

    // Tương đương với file_list_name và file_list_path
    private var fileListName = ArrayList<String>()
    private var fileListPath = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Kiểm tra quyền truy cập bộ nhớ
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission granted")
        } else {
            Log.v("TAG", "Permission denied")
            // Yêu cầu quyền truy cập
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1234)
        }

        // Lấy dữ liệu từ Intent (nếu có)
        val intent = intent
        val tempName = intent.getStringArrayListExtra("file_list_name")
        val tempPath = intent.getStringArrayListExtra("file_list_path")

        if (tempName != null && tempPath != null) {
            // Gán tạm vào biến toàn cục
            fileListName = tempName
            fileListPath = tempPath
        }

        // Nếu có dữ liệu từ Activity trước, hiển thị folder tương ứng
        if (fileListName.isNotEmpty()) {
            for (i in fileListName.indices) {
                folders.add(
                    Folder(
                        R.drawable.avatar,
                        fileListName[i],
                        fileListPath[i]
                    )
                )
            }
        } else {
            // Không có dữ liệu => lấy danh sách thư mục gốc
            val files: Array<File>? = Environment.getExternalStorageDirectory().listFiles()
            files?.forEach { f ->
                folders.add(
                    Folder(
                        R.drawable.avatar,
                        f.name,
                        f.absolutePath
                    )
                )
            }
        }

        // Ánh xạ RecyclerView
        recyclerView = findViewById(R.id.folder_list)
        recyclerView.setHasFixedSize(true)

        // Khởi tạo Adapter
        val adapter = FoldertAdapter(folders, this)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val tempName = ArrayList<String>()
        val tempPath = ArrayList<String>()
        val folder = folders[position]

        val directory = File(folder.path)
        val files = directory.listFiles()
        if (files != null) {
            // Nếu là thư mục, duyệt các file/folder bên trong
            for (f in files) {
                tempName.add(f.name)
                tempPath.add(f.absolutePath)
            }
            openActivity(tempName, tempPath)
        } else {
            // Nếu là file thì mở đọc nội dung
            try {
                FileInputStream(folder.path).use { fis ->
                    val reader = BufferedReader(InputStreamReader(fis))
                    val builder = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        builder.append(line).append("\n")
                    }
                    reader.close()

                    openContent(builder.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Hàm callback sau khi người dùng cấp hoặc từ chối quyền
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Tuỳ ý xử lý kết quả
        // if (requestCode == 1234) { ... }
    }

    // Mở lại Activity chính kèm theo danh sách file/folder con
    private fun openActivity(name: ArrayList<String>, path: ArrayList<String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putStringArrayListExtra("file_list_name", name)
        intent.putStringArrayListExtra("file_list_path", path)
        startActivity(intent)
    }

    // Mở Activity khác để hiển thị nội dung file
    private fun openContent(content: String) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("content", content)
        startActivity(intent)
    }
}
