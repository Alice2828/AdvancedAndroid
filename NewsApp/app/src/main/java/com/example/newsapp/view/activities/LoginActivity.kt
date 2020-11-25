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
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE)
        if (sharedPreferences.getString("username", "")?.isNotEmpty()!!) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_login)
    }

    fun onClick(view: View) {
        val emailName = email.text.toString()
        val password = password.text.toString()
        val registeredSharedPreferences = getSharedPreferences("registered", MODE_PRIVATE)

        if (emailName.isNotEmpty() && password.isNotEmpty()) {
            if (registeredSharedPreferences.getString(emailName, "")?.isNotEmpty()!!) {
                if (password == registeredSharedPreferences.getString("password$emailName", "")) {
                    val editor = sharedPreferences.edit()
                    editor.putString("emailName", emailName)
                    editor.putString(
                        "username",
                        registeredSharedPreferences.getString("username$emailName", "")
                    )
                    editor.putString("password", password)
                    editor.apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Wrong password", Toast.LENGTH_LONG)
                        .show()
                }
            } else
                Toast.makeText(this@LoginActivity, "You are not registered", Toast.LENGTH_LONG)
                    .show()
        } else
            Toast.makeText(this@LoginActivity, "Empty email or password", Toast.LENGTH_LONG)
                .show()
    }

    fun onRegister(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}