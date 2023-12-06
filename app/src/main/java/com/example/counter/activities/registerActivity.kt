package com.example.counter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.counter.R
import com.parse.LogInCallback
import com.parse.ParseUser
import com.parse.SignUpCallback

class registerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var register : Button = findViewById(R.id.register)
        var username : EditText = findViewById(R.id.registerUser)
        var pass : EditText = findViewById(R.id.registerPass)



        register.setOnClickListener {
            val user  = ParseUser()
            user.username = username.text.toString()
            user.setPassword(pass.text.toString())

            user.signUpInBackground(SignUpCallback() {
                if (it != null) {
                    Log.e("Registery", "SignUpException: " + it)
                }
                ParseUser.logInInBackground(
                    username.text.toString(),
                    pass.text.toString(),
                    LogInCallback { _, e ->
                        if (e != null) {
                            Log.e("logInInBackground", "Exception = " + e)
                            return@LogInCallback
                        }
                        goMainActivity()
                    })
            })
        }
    }

    private fun goMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}