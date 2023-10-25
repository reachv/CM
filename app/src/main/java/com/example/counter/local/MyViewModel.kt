package com.example.counter.local
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counter.activities.MainActivity

import kotlinx.coroutines.launch

class MyViewModel : ViewModel(){
    private val prefs = MyPreferencesRepository.get()

    fun saveInput(s:String, index:Int){
        viewModelScope.launch {
            prefs.saveInput(s, index)
            Log.v("MyViewModel", "Done Saving input #$index")
        }
    }

    fun loadInputs(act: MainActivity){
        viewModelScope.launch {

        }
    }

}