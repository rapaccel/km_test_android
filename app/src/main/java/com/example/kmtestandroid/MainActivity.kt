package com.example.kmtestandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kmtestandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {
            val text = binding.etPalindrome.text.toString().trim()

            if (text.isEmpty()) {
                Toast.makeText(this, "Please input text", Toast.LENGTH_SHORT).show()
            } else {
                if (isPalindrome(text)) {
                    showDialog("Palindrome", "\"$text\" adalah palindrome!")
                } else {
                    showDialog("Bukan Palindrome", "\"$text\" bukan palindrome.")
                }
            }
        }

        binding.btnNext.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please input your name", Toast.LENGTH_SHORT).show()
            } else {
                val name = binding.etName.text.toString()
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }
    fun isPalindrome(input: String): Boolean {
        val cleanedInput = input.replace("\\s".toRegex(), "").lowercase()
        return cleanedInput == cleanedInput.reversed()
    }

    fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}