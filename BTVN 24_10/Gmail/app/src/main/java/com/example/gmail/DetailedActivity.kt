package com.example.gmail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gmail.databinding.ActivityDetailedBinding

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent =this.intent
        if (intent != null){
            val name = intent.getStringExtra("name")
            val content = intent.getIntExtra("content", R.string.fb_detail)
            val image = intent.getIntExtra("image",R.drawable.fb)

            binding.detailName.text = name
            binding.detailContent.setText(content)
            binding.detailImage.setImageResource(image)
        }
    }
}