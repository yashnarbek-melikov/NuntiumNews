package com.example.nuntiumnews.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.ActivityMainBinding
import com.example.nuntiumnews.utils.MySharedPreference
import com.example.nuntiumnews.utils.ThemeHelper
import com.yariksoffice.lingver.Lingver

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mySharedPreference: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mySharedPreference = MySharedPreference(this)

        if (mySharedPreference.getPreferences("isDark") == "1") {
            window?.statusBarColor = ContextCompat.getColor(this, R.color.white)
            ThemeHelper.applyTheme(ThemeHelper.darkMode)
        } else {
            window?.statusBarColor = ContextCompat.getColor(this, R.color.white)
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            ThemeHelper.applyTheme(ThemeHelper.lightMode)
        }
    }
}