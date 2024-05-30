package com.example.newuts.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.newuts.R
import com.example.newuts.utils.Station
import com.example.newuts.utils.Uts


class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)


        done()
        android.os.Handler().postDelayed(Runnable {
            val i = Intent(this@splashScreen, MainActivity::class.java)
            startActivity(i)
        }, 2000)

    }
    fun done()
    {
val uts= Uts(this)
        uts.save("isEnabled", ""+0)
        uts.save("isEnabled2", ""+0)
        uts.setMtcurrentSource(Station("STN","Station Name","Station"))
        uts.setMtcurrentDest(Station("STN","Station Name","Station"))
        uts.setPtcurrentStation(Station("STN","Station Name","Station"))

    }
}