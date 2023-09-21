package com.example.counter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.parse.LogInCallback
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var register : Button = findViewById(R.id.loginRegister)
        var login : Button = findViewById(R.id.loginLogButton)
        var username : EditText = findViewById(R.id.loginUsername)
        var password : EditText = findViewById(R.id.loginPassword)


        register.setOnClickListener {
            var intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            ParseUser.logInInBackground(username.text.toString(), password.text.toString(), LogInCallback { user, e ->
                if (e != null) {
                    Log.e("Login Failure:", e.toString())
                    return@LogInCallback
                }
                goMainActivity()
            })
            }

    }

    private fun goMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}