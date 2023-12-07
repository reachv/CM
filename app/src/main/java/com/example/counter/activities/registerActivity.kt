package com.example.counter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.counter.R
import com.parse.LogInCallback
import com.parse.ParseUser
import com.parse.SignUpCallback

class registerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var register: Button = findViewById(R.id.register)
        var username: EditText = findViewById(R.id.registerUser)
        var pass: EditText = findViewById(R.id.registerPass)
        var back: Button = findViewById(R.id.back)

        back.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            val user = ParseUser()
            user.username = username.text.toString()
            user.setPassword(pass.text.toString())
            var temp = ArrayList<Int>()
            user.put("favorites", temp)
            var userQuery = ParseUser.getQuery()
            userQuery.findInBackground { objects, e ->
                if (e != null) {
                    Log.e("Registery", "Query Exception: " + e)
                    return@findInBackground
                }
                for (i in objects) {
                    if (i.username.equals(username.text.toString())) {
                        Toast.makeText(
                            this,
                            "Username already exist, Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@findInBackground
                    }
                }
                if(pass.length() < 1){
                    Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show()
                    return@findInBackground
                }
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
    }
    private fun goMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}