package com.example.nuntiumnews.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nuntiumnews.databinding.ActivityMainBinding
import com.example.nuntiumnews.utils.MySharedPreference
import com.example.nuntiumnews.utils.ThemeHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mySharedPreference: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mySharedPreference = MySharedPreference(this)

        if (mySharedPreference.getPreferences("isDark") == "1") {
            ThemeHelper.applyTheme(ThemeHelper.darkMode)
        } else {
            ThemeHelper.applyTheme(ThemeHelper.lightMode)
        }
    }
}