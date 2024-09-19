package com.example.cellularautomata

import DataClass.Constants
import android.content.Intent
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
        val sharedPreferences=getSharedPreferences(getString(R.string.preference_file), MODE_PRIVATE)

        if(sharedPreferences.getBoolean("login",false) && sharedPreferences.getBoolean("home",false)){
            val intent= Intent(this@LoginActivity,HomeActivity::class.java)
            startActivity(intent)
        }
//        binding.etEmail.setText(mainEmail)
//        binding.etPassword.setText(mainPassword)
        binding.btnLogin.setOnClickListener {
            val email=binding.etEmail.text.toString()
            val password=binding.etPassword.text.toString()
            if(email==mainEmail && mainPassword==password)
            {
                sharedPreferences.edit().putBoolean("login",true).apply()
                sharedPreferences.edit().putBoolean("home",true).apply()
                Toast.makeText(this@LoginActivity,"Login",Toast.LENGTH_SHORT).show()
                val intent= Intent(this@LoginActivity,HomeActivity::class.java).apply {
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this@LoginActivity,"Invalid\nCredential",Toast.LENGTH_SHORT).show()
                if(email!=mainEmail)
                {
                    binding.etEmail.requestFocus()
                    binding.etEmail.error="Invalid Email"
                }
                if(password!=mainPassword)
                {
                    binding.etPassword.requestFocus()

                    binding.etPassword.error="Invalid Password"
                }
            }
        }

    }
    override fun onBackPressed() {
        finish()
    }

}