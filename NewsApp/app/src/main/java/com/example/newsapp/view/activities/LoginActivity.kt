package com.example.newsapp.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapp.R
import com.example.newsapp.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        if (!CommonUtils.isNightModeEnabled(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        } else if (CommonUtils.isNightModeEnabled(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

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
        val emailNameShP = registeredSharedPreferences.getString(emailName, "")
        val passwordShP = registeredSharedPreferences.getString(
            "password$emailName",
            ""
        )
        val userNameShP = registeredSharedPreferences.getString("username$emailName", "")
        if (validateNotEmpty(emailName, password)) {
            if (validateUserExist(emailNameShP!!)) {
                if (validatePassword(password, passwordShP!!)) {
                    val editor = sharedPreferences.edit()
                    editor.putString("emailName", emailName)
                    editor.putString(
                        "username",
                        userNameShP
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

internal fun validateNotEmpty(emailName: String, password: String): Boolean {
    if (emailName.isNotEmpty() && password.isNotEmpty()) return true
    return false
}

internal fun validateUserExist(emailName: String): Boolean {
    if (emailName.isNotEmpty()) return true
    return false
}

internal fun validatePassword(password: String, passwordShP: String): Boolean {
    if (password == passwordShP) return true
    return false
}