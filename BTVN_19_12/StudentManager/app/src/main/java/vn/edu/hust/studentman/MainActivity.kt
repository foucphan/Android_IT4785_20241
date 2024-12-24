package vn.edu.hust.studentman

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private lateinit var dbHelper: DBHelper

  private val students = mutableListOf<StudentModel>()

  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    dbHelper = DBHelper(this)

    val dbStudents = dbHelper.getAllStudents()

    // Nếu DB trống, chèn dữ liệu mặc định
    if (dbStudents.isEmpty()) {
      // Danh sách mặc định
      val defaultStudents = listOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
      )

      // Thêm tất cả vào DB
      for (student in defaultStudents) {
        dbHelper.addStudent(student)
      }
    }

    students.clear()
    students.addAll(dbHelper.getAllStudents())

    studentAdapter = StudentAdapter(
      this,
      students,
      onEditClick = { student ->
        openEditStudentFragment(student)
      },
      onRemoveClick = { student ->
        removeStudentFromDB(student)
      }
    )

    val listView = findViewById<ListView>(R.id.list_view_students)
    listView.adapter = studentAdapter

    registerForContextMenu(listView)

    listView.setOnItemClickListener { _, _, position, _ ->
      val student = students[position]
      openEditStudentFragment(student)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      openAddStudentFragment()
    }
  }

  fun addStudent(newStudent: StudentModel) {
    val result = dbHelper.addStudent(newStudent)
    if (result != -1L) {
      Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show()
      students.clear()
      students.addAll(dbHelper.getAllStudents())
      studentAdapter.notifyDataSetChanged()
    } else {
      Toast.makeText(this, "Lỗi khi thêm!", Toast.LENGTH_SHORT).show()
    }
  }

  fun updateStudent(oldCode: String, updatedStudent: StudentModel) {
    val result = dbHelper.updateStudent(oldCode, updatedStudent)
    if (result > 0) {
      Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
      students.clear()
      students.addAll(dbHelper.getAllStudents()) // Tải lại danh sách từ DB
      studentAdapter.notifyDataSetChanged()
    } else {
      Toast.makeText(this, "Lỗi khi cập nhật!", Toast.LENGTH_SHORT).show()
    }
  }

  private fun removeStudentFromDB(student: StudentModel) {
    val result = dbHelper.removeStudent(student.studentId)
    if (result > 0) {
      Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
      students.clear()
      students.addAll(dbHelper.getAllStudents()) // Tải lại danh sách từ DB
      studentAdapter.notifyDataSetChanged()
    } else {
      Toast.makeText(this, "Lỗi khi xóa!", Toast.LENGTH_SHORT).show()
    }
  }

  private fun openAddStudentFragment() {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, AddStudentFragment())
    transaction.addToBackStack(null)
    transaction.commit()
  }

  private fun openEditStudentFragment(student: StudentModel) {
    val transaction = supportFragmentManager.beginTransaction()
    val fragment = EditStudentFragment.newInstance(student, student.studentId)
    transaction.replace(R.id.container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menu.setHeaderTitle("Options")
    menu.add(0, 0, 0, "Edit")
    menu.add(0, 1, 1, "Remove")
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val student = students[info.position]

    when (item.itemId) {
      0 -> {
        // Edit option selected
        openEditStudentFragment(student)
        return true
      }
      1 -> {
        // Remove option selected
        removeStudentFromDB(student) // Gọi hàm để xóa khỏi DB
        return true
      }
    }
    return super.onContextItemSelected(item)
  }
}