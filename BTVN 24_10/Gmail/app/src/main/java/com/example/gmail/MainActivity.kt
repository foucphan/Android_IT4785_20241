package com.example.gmail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import com.example.gmail.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
    private lateinit var listData: ListData
    var dataArrayList = ArrayList<ListData?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameList = arrayOf("Facebook", "Instagram", "Gmail","Twitter", "Youtube")
        val timeList = arrayOf("12:34PM", "11:22AM", "11:04AM", "10:26AM", "3:10PM")

        val imageList = intArrayOf(
            R.drawable.fb,
            R.drawable.insta,
            R.drawable.gmail,
            R.drawable.twitter,
            R.drawable.youtube
        )
        val content = intArrayOf(
            R.string.fb_detail,
            R.string.insta_detail,
            R.string.gmail_detail,
            R.string.twitter_detail,
            R.string.youtube_detail
        )

        for(i in imageList.indices){
            listData = ListData(nameList[i],timeList[i],content[i],imageList[i])
            dataArrayList.add(listData)
        }
        listAdapter = ListAdapter(this@MainActivity, dataArrayList)
        binding.listView.adapter = listAdapter
        binding.listView.isClickable = true

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view,i ,l ->
            val intent = Intent(this@MainActivity, DetailedActivity::class.java)
            intent.putExtra("name",nameList[i])
            intent.putExtra("time",timeList[i])
            intent.putExtra("content",content[i])
            intent.putExtra("image",imageList[i])
            startActivity(intent)
        }

    }
}