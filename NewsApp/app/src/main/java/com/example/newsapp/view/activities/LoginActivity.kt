package com.example.newsapp.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE)
        if (sharedPreferences.getString("username", "")?.isNotEmpty()!!) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_login)
    }

    fun onClick(view: View) {
        val username = email.text.toString()
        val password = password.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else
            Toast.makeText(this@LoginActivity, "Empty email or password", Toast.LENGTH_LONG)
                .show()
    }
}