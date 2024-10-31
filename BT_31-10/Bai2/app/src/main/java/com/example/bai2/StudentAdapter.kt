package com.example.bai2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private var studentList: MutableList<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var studentListFull: List<Student> = ArrayList(studentList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = studentList[position]
        holder.studentName.text = currentStudent.name
        holder.studentId.text = currentStudent.studentId
    }

    override fun getItemCount(): Int = studentList.size

    fun filter(text: String) {
        studentList.clear()
        if (text.length > 2) {
            for (student in studentListFull) {
                if (student.name.contains(text, ignoreCase = true) ||
                    student.studentId.contains(text, ignoreCase = true)) {
                    studentList.add(student)
                }
            }
        } else {
            studentList.addAll(studentListFull)
        }
        notifyDataSetChanged()
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.student_name)
        val studentId: TextView = itemView.findViewById(R.id.student_id)
    }
}