package com.example.newsapp.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.login
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.register
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        sharedPreferences = getSharedPreferences("registered", MODE_PRIVATE)

        login.setOnClickListener {
            onBackPressed()
        }

        register.setOnClickListener {
            val username = name.text.toString()
            val emailName = email.text.toString()
            val password = password.text.toString()
            val userNameTag = "username$emailName"
            val passwordTag = "password$emailName"

            if (sharedPreferences.getString(emailName, "")?.isNotEmpty()!!) {
                Toast.makeText(this, "Email is not free", Toast.LENGTH_LONG).show()
            } else {
                if (username.isNotEmpty() && password.isNotEmpty() && emailName.isNotEmpty()) {
                    val editor = sharedPreferences.edit()
                    editor.putString(userNameTag, username)
                    editor.putString(emailName, emailName)
                    editor.putString(passwordTag, password)
                    editor.apply()
                    Toast.makeText(
                        this@RegistrationActivity,
                        "You are successfully registered!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Empty email or password",
                        Toast.LENGTH_LONG
                    )
                        .show()
            }
        }
    }
}
