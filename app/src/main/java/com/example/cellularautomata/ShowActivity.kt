package com.example.cellularautomata

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cellularautomata.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = intent.getStringExtra("title")

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val data=intent.getStringExtra("data")
        if (data == null){
            binding.textShow.text="NULL"
        }
        else{
            binding.textShow.text=data
        }

    }
}