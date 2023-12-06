package com.example.counter.parse

import android.app.Application
import com.parse.Parse

class parse : Application(){
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("Knogl3QNo4lcCAwq1sbT1dNoKuUXSU0iMpxKJw73")
                .clientKey("NoJoB4y1xoO4ZKIyqFjQBfKyTZTtmiXvcv56rIHp")
                .server("https://parseapi.back4app.com")
                .build()
        )
    }
}