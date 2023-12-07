package com.example.counter.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.counter.R
import com.example.counter.activities.LoginActivity
import com.example.counter.activities.passwordActivity
import com.example.counter.local.MyPreferencesRepository
import com.example.counter.local.MyViewModel
import com.parse.ParseUser

class SettingsFragment : Fragment() {
    lateinit var email : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private val myViewModel: MyViewModel by lazy {
        MyPreferencesRepository.initialize(requireContext())
        ViewModelProvider(this)[MyViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        var user = view.findViewById<EditText>(R.id.settingsUser)
        var pass = view.findViewById<TextView>(R.id.settingsPassword)
        var logout : Button = view.findViewById(R.id.logout)
        var save : Button = view.findViewById(R.id.Save)

        email  = view.findViewById(R.id.email)
        this.myViewModel.loadInputs(this)

        user.setText(ParseUser.getCurrentUser().username)
        save.setOnClickListener {
            myViewModel.saveInput(email.text.toString(), 1)
            //Queries usernames
            var query = ParseUser.getQuery()
            query.findInBackground { objects, e ->
                if (e != null) {
                    Log.e("SettingsQueryException: ", e.toString())
                    return@findInBackground
                }
                //Changes username
                var pUser = ParseUser.getCurrentUser()
                pUser.username = user.text.toString()
                pUser.saveInBackground {
                    if(it != null){
                        Log.e("SettingsSaveException: " , it.toString())
                        Toast.makeText(context, "Username already exist", Toast.LENGTH_SHORT).show()
                        return@saveInBackground
                    }
                    Toast.makeText(context, "Successfully changed username", Toast.LENGTH_SHORT).show()
                }
            }
        }


        //Logs user out
        logout.setOnClickListener {
            ParseUser.logOutInBackground {
                if(it != null){
                    Log.e("SettingFragmentsLogoutException: ", "" + it)
                    return@logOutInBackground
                }
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        //Change password activity
        pass.setOnClickListener {
            val intent = Intent(context, passwordActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}