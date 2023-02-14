package com.example.nuntiumnews.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nuntiumnews.databinding.ActivityMainBinding
import com.example.nuntiumnews.utils.MySharedPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}