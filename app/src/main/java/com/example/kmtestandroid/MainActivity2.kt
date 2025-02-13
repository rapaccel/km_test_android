package com.example.kmtestandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kmtestandroid.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra("name")?.let {
            binding.tvName.text = it
        }

        binding.backSecondScreen.setOnClickListener {
            finish()
        }
        intent.getStringExtra("selectedName")?.let {
            binding.tvSelectedName.text = it
        }

        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}