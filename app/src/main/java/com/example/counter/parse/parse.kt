package com.example.counter.parse

import android.app.Application
import com.parse.Parse

class parse : Application(){
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("qh7n5Avix4he8pKe13Qy0o1h3pcN9dlT3RG5ChWp")
                .clientKey("GmkwmJJ22XKgYrKCushrHvElxnPddc2KksnlXQXk")
                .server("https://parseai.back4app.com")
                .build()
        );
    }
}