package vn.edu.hust.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class EditStudentFragment : Fragment(R.layout.fragment_edit_student) {

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var updateButton: Button

    companion object {
        private const val ARG_STUDENT = "student"
        private const val ARG_OLD_CODE = "oldCode"

        // Hàm tạo instance kèm dữ liệu StudentModel
        fun newInstance(student: StudentModel, oldCode: String): EditStudentFragment {
            val fragment = EditStudentFragment()
            val args = Bundle().apply {
                putSerializable(ARG_STUDENT, student)
                putString(ARG_OLD_CODE, oldCode)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.edit_student_name)
        idEditText = view.findViewById(R.id.edit_student_id)
        updateButton = view.findViewById(R.id.btn_update_student)

        // Lấy student được truyền vào qua arguments
        @Suppress("DEPRECATION")
        val student = arguments?.getSerializable(ARG_STUDENT) as? StudentModel
        val oldCode = arguments?.getString(ARG_OLD_CODE)

        // Hiển thị thông tin ban đầu
        student?.let {
            nameEditText.setText(it.studentName)
            idEditText.setText(it.studentId)
        }

        updateButton.setOnClickListener {
            if (student != null && oldCode != null) {
                val updatedStudent = StudentModel(
                    studentName = nameEditText.text.toString().trim(),
                    studentId = idEditText.text.toString().trim()
                )

                (activity as? MainActivity)?.updateStudent(oldCode, updatedStudent)

                // Quay lại Fragment trước
                parentFragmentManager.popBackStack()
            }
        }
    }
}
