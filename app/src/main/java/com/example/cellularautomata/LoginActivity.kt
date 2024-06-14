package com.example.cellularautomata

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cellularautomata.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mainEmail:String
    private lateinit var mainPassword:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainEmail="s@gmail.com"
        mainPassword="sugam123"
        binding.btnLogin.setOnClickListener {
            val email=binding.etEmail.text.toString()
            val password=binding.etPassword.text.toString()

            if(email==mainEmail && mainPassword==password){
                Toast.makeText(this@LoginActivity,"Login",Toast.LENGTH_LONG).show()

            }
            else{
                Toast.makeText(this@LoginActivity,"Invalid\nEmail",Toast.LENGTH_LONG).show()
            }
        }

    }
}