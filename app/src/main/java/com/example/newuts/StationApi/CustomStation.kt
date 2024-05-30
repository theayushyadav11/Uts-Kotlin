package com.example.newuts.StationApi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newuts.R
import com.example.newuts.activities.SelectStation
import com.example.newuts.databinding.ActivityCustomStationBinding
import com.example.newuts.utils.Station
import com.example.newuts.utils.Uts

class CustomStation : AppCompatActivity() {
    private lateinit var binding:ActivityCustomStationBinding
    private lateinit var uts: Uts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uts= Uts(this)
        binding.done.setOnClickListener{
            done()
        }


    }
    fun done()
    {
     var eng=binding.staeng.text.toString().uppercase()
     var hin=binding.stahindi.text.toString().uppercase()
     var code=binding.stacode.text.toString().uppercase()
        if(!(eng.isEmpty()||code.isEmpty()||hin.isEmpty()||isAlphabetic(code)||isAlphabetic(hin)||isAlphabetic(eng)))
            {
              uts.addLocalStation(Station(code,eng, hin))
                uts.toast("Station Added successfully")
                uts.navigate(this,SelectStation::class.java)

            }
        else
        {
            uts.toast("Invalid station")
        }
    }
    fun isAlphabetic(str: String): Boolean {
        return str.all { !it.isLetter() }
    }
}