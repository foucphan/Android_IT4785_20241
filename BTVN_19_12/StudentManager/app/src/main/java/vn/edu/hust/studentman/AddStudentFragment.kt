package vn.edu.hust.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AddStudentFragment : Fragment(R.layout.fragment_add_student) {

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var addButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.edit_student_name)
        idEditText = view.findViewById(R.id.edit_student_id)
        addButton = view.findViewById(R.id.btn_add_student)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val id = idEditText.text.toString().trim()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                // Tạo đối tượng StudentModel
                val newStudent = StudentModel(name, id)

                // Gọi hàm addStudent bên MainActivity để thêm vào danh sách (và DB nếu có)
                (activity as? MainActivity)?.addStudent(newStudent)

                // Quay lại Fragment trước
                parentFragmentManager.popBackStack()
            }
        }
    }
}
