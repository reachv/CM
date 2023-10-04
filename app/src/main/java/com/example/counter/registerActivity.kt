package com.example.counter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.parse.ParseUser

class registerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var register : Button = findViewById(R.id.register)
        var user : EditText = findViewById(R.id.registerUser)
        var pass : EditText = findViewById(R.id.registerPass)



        register.setOnClickListener {
            var user = ParseUser()
            user.username = user.username.toString()
            user.setPassword(pass.text.toString())

            user.signUpInBackground {
                if(it != null){
                    Log.e("Register", "exception: " + it.toString())
                    return@signUpInBackground
                }
                goMainActivity()
            }
        }
    }

    private fun goMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}