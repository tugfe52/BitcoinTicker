package com.development.bitcointicker.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.development.bitcointicker.R
import com.development.bitcointicker.databinding.ActivityStartBinding
import com.development.bitcointicker.view.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private lateinit var startActivityBinding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivityBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(startActivityBinding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this@StartActivity, AuthActivity::class.java))
                finish()
            }, 3000
        )
    }
}