package com.example.newuts.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.newuts.R
import com.example.newuts.StationApi.CustomStation
import com.example.newuts.StationApi.SearchQuery
import com.example.newuts.StationApi.StationInstance
import com.example.newuts.databinding.ActivitySelectStationBinding
import com.example.newuts.databinding.FragmentPlatformTicketBinding
import com.example.newuts.translateApi.RetrofitInstance
import com.example.newuts.translateApi.Translation
import com.example.newuts.utils.Station
import com.example.newuts.utils.Uts
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class SelectStation : AppCompatActivity() {
    lateinit var binding: ActivitySelectStationBinding
   private  var mode:Int = 0
    private lateinit var localStations: ArrayList<Station>
    private var searchStations: ArrayList<Station> = arrayListOf()
    private lateinit var selectedStation: Station
    lateinit var uts: Uts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localStations = Uts(this).getLocalStations()

        uts = Uts(this)
        mode=intent.getIntExtra("mode",0)
        if(mode ==0)
        {
            binding.title.text = "Select Source Station"
        }
        if(mode ==1)
        {
            binding.title.text = "Select Destination Station"
        }
        binding.btnsearch.setOnClickListener {

            binding.result.removeAllViews()
            search(binding.search.text.toString())
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }



        addlocal(localStations)
        binding.addCustom.setOnClickListener{
            uts.navigate(this,CustomStation::class.java)
        }

    }


    fun search(c: String) {
        binding.pb.isVisible = true
        hideKeyboard()
        binding.result.removeAllViews()
        searchStations.clear()


        if (c.length > 1) {
            try {
                lifecycleScope.coroutineContext.cancelChildren()
                val query = SearchQuery(c)
                lifecycleScope.launch {
                    try {
                        val response = StationInstance.api.getStations(query)
                        val sta = response
                        println(sta)

                        try {
                            onResponse(sta)
                        } catch (e: Exception) {

                        }
                    } catch (e: Exception) {
                        Log.d("ayu3sh", "error")
                    }
                }
            } catch (e: Exception) {
                Log.d("ayush", "error")
                e.printStackTrace()
            }
        } else {
            binding.pb.isVisible = false
        }


    }

    fun onResponse(response: List<List<String>>) {
        binding.localhistory.removeAllViews()
        //   Log.d(TAG, "Response: $response")
        var p = ""
        var q = ""
        var r = ""
        for (i in 0 until response.size) {
            p += response[i][0] + "*"

        }
        for (i in 0 until response.size) {
            q += response[i][1].uppercase() + "*"

        }
        println(p)






        for (i in 0 until response.size) {
            r += response[i][1] + "*"
        }
        r = r.substring(0, r.length - 1)

        if (q.length > 1) {
            var k = r.replace("Jn", "")

            Log.d("city", k)
            val to = Translation("en", "hi", k)
            try {
                lifecycleScope.launch {
                    val res = RetrofitInstance.api.translate(to)

                    var s = res.body()?.translation.toString()
                    s = s.replace("शहर", "सिटी")
                    r = r.substring(0, r.length)
                    s = s.substring(0, s.length)
                    p = p.substring(0, p.length)

                    binding.pb.isVisible = false;

                    var code = p.split("*")
                    var eng = r.uppercase().split("*")
                    var hin = s.toString().split("*")
                    println(code)
                    for (i in 0 until eng.size) {
                        searchStations.add(Station(code[i], eng[i], hin[i]))
                    }
                    println(searchStations)
                    binding.result.removeAllViews()

                    for (i in 0 until searchStations.size) {
                        try {


                            val inflater = LayoutInflater.from(this@SelectStation)
                            val list =
                                inflater.inflate(R.layout.list_element, binding.result, false)
                            list.findViewById<TextView>(R.id.element).text =
                                searchStations[i].code + "-" + searchStations[i].nameEnglish
                            list.setOnClickListener {
                                selectedStation=searchStations[i]
                                if(uts.getLocalStations().contains(selectedStation))
                                {
                                    uts.removeStation(selectedStation)
                                }



                                uts.addLocalStation(searchStations[i])
                                Toast.makeText(

                                    this@SelectStation,
                                    searchStations[i].nameHindi,
                                    Toast.LENGTH_SHORT
                                ).show()

                                if(mode==0)
                                {
                                    if(!(uts.getMtcurrentDest()==selectedStation))
                                    {
                                     //   uts.setMtSource(selectedStation)
                                        uts.setMtcurrentSource(selectedStation)
                                        uts.navigate(this@SelectStation, MainActivity::class.java)
                                    }
                                    else
                                    {
                                        uts.toast("Source Station cannot be same as Destination  Station  ")
                                    }
                                }
                                else if(mode==1)
                                {
                                    if(!(uts.getMtcurrentSource()==selectedStation))
                                    {
                                       // uts.setMtDest(selectedStation)
                                        uts.setMtcurrentDest(selectedStation)
                                        uts.navigate(this@SelectStation, MainActivity::class.java)
                                    }
                                    else
                                    {
                                        uts.toast("Destination Station cannot be same as Source Station  ")
                                    }

                                }
                                else
                                    if(mode==2)
                                    {
                                        val intent= Intent(this@SelectStation, MainActivity::class.java)
                                        intent.putExtra("mode",2)
                                    //    uts.setPtStation(selectedStation)
                                        uts.setPtcurrentStation(selectedStation)
                                        startActivity(intent)
                                    }





                            }

                            binding.result.addView(list)
                            binding.pb.isVisible = false
                        } catch (e: Exception) {
                        }

                    }


                }
            } catch (e: Exception) {
            }

        }

    }


    private fun hideKeyboard() {
        // Get the InputMethodManager
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // Find the currently focused view, so we can grab the correct window token from it.
        val view = currentFocus ?: View(this)
        // Hide the keyboard
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun addlocal(local: ArrayList<Station>) {

        binding.localhistory.removeAllViews()
        try {
            for (i in 0 until localStations.size) {
                var list = LayoutInflater.from(this)
                    .inflate(R.layout.list_element, binding.localhistory, false)
                list.findViewById<TextView>(R.id.element).text =
                    localStations[i].code + "-" + localStations[i].nameEnglish
                list.findViewById<ImageView>(R.id.delete).isVisible = true
                list.setOnClickListener{
                    selectedStation=localStations[i]
                    if(mode==0)
                    {
                        if(!(uts.getMtcurrentDest()==selectedStation))
                        {
                          //  uts.setMtSource(selectedStation)
                            uts.setMtcurrentSource(selectedStation)
                            uts.navigate(this@SelectStation, MainActivity::class.java)
                        }
                        else
                        {
                            uts.toast("Source Station cannot be same as Destination  Station ")
                        }
                    }
                    else if(mode==1)
                    {
                        if(!(uts.getMtcurrentSource()==selectedStation))
                        {
                          //  uts.setMtDest(selectedStation)
                            uts.setMtcurrentDest(selectedStation)

                            uts.navigate(this@SelectStation, MainActivity::class.java)
                        }
                        else
                        {
                            uts.toast("Destination Station cannot be same as Source Station  ")
                        }

                    }
                    else
                        if(mode==2)
                        {
                            val intent= Intent(this@SelectStation, MainActivity::class.java)
                            intent.putExtra("mode",2)
                          //  uts.setPtStation(selectedStation)
                            uts.setPtcurrentStation(selectedStation)
                          startActivity(intent)
                        }


                }
                list.findViewById<ImageView>(R.id.delete).setOnClickListener {
                    uts.removeStation(localStations[i])
                    localStations = uts.getLocalStations()
                    addlocal(localStations)


                }

                binding.localhistory.addView(list)

            }
        } catch (e: Exception) {
            println("ayush yadav is ")
        }

    }

}

