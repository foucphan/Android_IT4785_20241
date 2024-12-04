package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentAdapter

  private val students = mutableListOf(
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val btnAddNew: Button = findViewById(R.id.btn_add_new)

    btnAddNew.setOnClickListener {
      showAddStudentDialog()
    }

    studentAdapter = StudentAdapter(students) { student, position ->
      showEditStudentDialog(student, position, studentAdapter)
    }

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }
  }

  private fun showEditStudentDialog(
    student: StudentModel,
    position: Int,
    adapter: StudentAdapter
  ) {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.edit_student, null)
    val edtName = dialogView.findViewById<EditText>(R.id.edt_edit_student_name)
    val edtId = dialogView.findViewById<EditText>(R.id.edt_edit_student_id)
    val btnConfirm = dialogView.findViewById<Button>(R.id.btn_confirm_edit)
    edtName.setText(student.studentName)
    edtId.setText(student.studentId)
    val dialog = AlertDialog.Builder(this)
      .setView(dialogView)
      .create()
    btnConfirm.setOnClickListener {
      val newName = edtName.text.toString().trim()
      val newId = edtId.text.toString().trim()

      if (newName.isNotEmpty() && newId.isNotEmpty()) {
        student.studentName = newName
        student.studentId = newId
        adapter.notifyItemChanged(position)
        dialog.dismiss()
        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
      }
    }
    dialog.show()
  }


  private fun showAddStudentDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.add_student, null)
    val edtName = dialogView.findViewById<EditText>(R.id.edt_student_name)
    val edtId = dialogView.findViewById<EditText>(R.id.edt_student_id)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Thêm Sinh Viên")
      .setView(dialogView)
      .create()
    dialogView.findViewById<Button>(R.id.btn_confirm_add).setOnClickListener {
      val name = edtName.text.toString().trim()
      val id = edtId.text.toString().trim()
      if (name.isEmpty() || id.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
      } else {
        students.add(StudentModel(name, id))
        Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
      }
    }
    dialog.show()
  }

  fun showDeleteConfirmationDialog(
    student: StudentModel,
    position: Int,
    adapter: StudentAdapter
  ) {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.confirm_delete, null)
    val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)
    val btnConfirmDelete = dialogView.findViewById<Button>(R.id.btn_confirm_delete)
    val dialog = AlertDialog.Builder(this)
      .setView(dialogView)
      .create()
    btnCancel.setOnClickListener {
      dialog.dismiss()
    }
    btnConfirmDelete.setOnClickListener {
      students.removeAt(position)
      adapter.notifyItemRemoved(position)
      dialog.dismiss()
      Toast.makeText(this, "Đã xóa sinh viên: ${student.studentName}", Toast.LENGTH_SHORT).show()
    }
    dialog.show()
  }

}
