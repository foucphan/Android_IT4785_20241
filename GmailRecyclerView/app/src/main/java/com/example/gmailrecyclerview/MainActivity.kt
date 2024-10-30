package com.example.gmailrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var imageList:Array<Int>
    lateinit var titleList:Array<String>
    lateinit var textList:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageList = arrayOf(
            R.drawable.fb,
            R.drawable.gmail,
            R.drawable.insta,
            R.drawable.twitter,
            R.drawable.youtube,
            R.drawable.teams,
            R.drawable.zalo,
            R.drawable.reddit,
            R.drawable.zalo
        )
        titleList = arrayOf(
            "Facebook",
            "Gmail",
            "Instagram",
            "Twitter",
            "Youtube",
            "Teams",
            "Zalo",
            "Reddit",
            "Zalo"
        )
        textList = arrayOf(
            "This is your Facebook notification",
            "This is your Gmail notification",
            "This is your Instagram notification",
            "This is your Twitter notification",
            "This is your Youtube notification",
            "This is your Teams notification",
            "This is your Zalo notification",
            "This is your Reddit notification",
            "none"
        )
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<DataClass>()
        getData()
    }
    private fun getData(){
        for (i in imageList.indices){
            val dataClass = DataClass(imageList[i], titleList[i], textList[i])
            dataList.add(dataClass)
        }
        recyclerView.adapter = AdapterClass(dataList)
    }
}