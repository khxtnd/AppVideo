package com.client.app.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.client.app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLoginRa.setOnClickListener {
            finish()
        }
        binding.btnRegisterRa.setOnClickListener {
            finish()
        }
    }
}