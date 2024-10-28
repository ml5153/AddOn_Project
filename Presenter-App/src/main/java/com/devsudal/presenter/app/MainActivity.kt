package com.devsudal.presenter.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devsudal.presenter.app.databinding.ActivityMainBinding
import com.devsudal.sdk.ph.PointHomeSDK

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivMainImage.setOnClickListener {
            PointHomeSDK.open(this@MainActivity)
        }
        binding.tvButton.setOnClickListener {
            PointHomeSDK.open(this@MainActivity)
        }
    }
}