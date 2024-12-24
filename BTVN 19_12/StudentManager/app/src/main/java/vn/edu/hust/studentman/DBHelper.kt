package vn.edu.hust.studentman

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DBHelper kế thừa SQLiteOpenHelper
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Tên bảng và tên cột
    companion object {
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Students"
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
        private const val COL_CODE = "code"
    }

    // Tạo bảng
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
          CREATE TABLE $TABLE_NAME (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NAME TEXT NOT NULL,
            $COL_CODE TEXT NOT NULL
        )
        """
        db?.execSQL(createTableQuery)
    }

    // Nâng cấp CSDL (khi thay đổi DATABASE_VERSION)
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Thêm sinh viên
    fun addStudent(student: StudentModel): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, student.studentName)
            put(COL_CODE, student.studentId)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    // Lấy danh sách sinh viên từ DB
    fun getAllStudents(): MutableList<StudentModel> {
        val studentList = mutableListOf<StudentModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
                val code = cursor.getString(cursor.getColumnIndexOrThrow(COL_CODE))
                // Tạo StudentModel từ dữ liệu
                val student = StudentModel(name, code)
                studentList.add(student)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList
    }

    // Cập nhật (sửa) thông tin sinh viên
    fun updateStudent(oldCode: String, newStudent: StudentModel): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, newStudent.studentName)
            put(COL_CODE, newStudent.studentId)
        }
        val result = db.update(
            TABLE_NAME,
            values,
            "$COL_CODE = ?",
            arrayOf(oldCode)
        )
        db.close()
        return result
    }

    // Xóa sinh viên
    fun removeStudent(studentCode: String): Int {
        val db = writableDatabase
        val result = db.delete(
            TABLE_NAME,
            "$COL_CODE = ?",
            arrayOf(studentCode)
        )
        db.close()
        return result
    }
}
