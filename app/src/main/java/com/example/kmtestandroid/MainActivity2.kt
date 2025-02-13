package com.example.kmtestandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

        }


        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        updateSelectedName()
    }
    private fun updateSelectedName() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("selectedName")) {
            val selectedName = sharedPreferences.getString("selectedName", "")
            binding.tvSelectedName.text = selectedName
        } else {
            binding.tvSelectedName.text = "Selected User Name"
        }

    }

}