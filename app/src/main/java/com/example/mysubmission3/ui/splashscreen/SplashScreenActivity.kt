package com.example.mysubmission3.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mysubmission3.databinding.ActivitySplashScreenBinding
import com.example.mysubmission3.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivLogoApp.alpha = 0f
        binding.ivLogoApp.animate().setDuration(1500).alpha(1f).withEndAction {
            val toMainActivity = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(toMainActivity)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}