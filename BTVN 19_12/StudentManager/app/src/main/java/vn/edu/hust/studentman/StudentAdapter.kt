package vn.edu.hust.studentman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import vn.edu.hust.studentman.R
import vn.edu.hust.studentman.StudentModel

class StudentAdapter(
  private val context: Context,
  private val students: List<StudentModel>,
  private val onEditClick: (StudentModel) -> Unit,
  private val onRemoveClick: (StudentModel) -> Unit
) : BaseAdapter() {

  override fun getCount(): Int {
    return students.size
  }

  override fun getItem(position: Int): Any {
    return students[position]
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view: View
    val holder: ViewHolder

    if (convertView == null) {
      val inflater = LayoutInflater.from(context)
      view = inflater.inflate(R.layout.layout_student_item, parent, false)

      holder = ViewHolder()
      holder.nameTextView = view.findViewById(R.id.text_student_name)
      holder.idTextView = view.findViewById(R.id.text_student_id)
      holder.editImageView = view.findViewById(R.id.image_edit)
      holder.removeImageView = view.findViewById(R.id.image_remove)

      view.tag = holder
    } else {
      view = convertView
      holder = view.tag as ViewHolder
    }

    val student = students[position]
    holder.nameTextView.text = student.studentName
    holder.idTextView.text = student.studentId

    // Gắn sự kiện cho nút chỉnh sửa
    holder.editImageView?.setOnClickListener {
      onEditClick(student)
    }

    // Gắn sự kiện cho nút xóa
    holder.removeImageView?.setOnClickListener {
      onRemoveClick(student)
    }

    return view
  }

  private class ViewHolder {
    lateinit var nameTextView: TextView
    lateinit var idTextView: TextView
    var editImageView: ImageView? = null
    var removeImageView: ImageView? = null
  }
}