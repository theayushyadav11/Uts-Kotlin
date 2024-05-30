package com.example.newuts.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newuts.activities.SelectStation
import com.example.newuts.activities.ShowTicket
import com.example.newuts.databinding.FragmentJourneyTicketBinding
import com.example.newuts.activities.DataEntry
import com.example.newuts.utils.Station
import com.example.newuts.utils.Uts


class JourneyTicketFragment : Fragment() {
    private var _binding: FragmentJourneyTicketBinding? = null
    private val binding get() = _binding!!
    private lateinit var uts: Uts

    private var isEnabled=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentJourneyTicketBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uts=Uts(context)
        updateSTN()
        listeners()





    }
      fun updateSTN()
      {
          binding.staabr.text=uts.getMtcurrentSource().code
          binding.staname.text=uts.getMtcurrentSource().nameEnglish

          binding.staabr2.text=uts.getMtcurrentDest().code
          binding.staname2.text=uts.getMtcurrentDest().nameEnglish
          var temp=uts.get("isEnabled")
          println(temp)
          if(temp=="1")
          {
              binding.rb2.isChecked=false
              binding.rb1.isChecked=true
              isEnabled=1
              uts.save("isEnabled", ""+isEnabled)
              uts.addBookAndTravel(binding.tempCard)
          }

          else if(temp=="2") {
              binding.rb1.isChecked = false
              binding.rb2.isChecked = true
              isEnabled = 2
              uts.save("isEnabled", "" + isEnabled)
              uts.addBookAndPrint(binding.tempCard)
          }

      }

    fun selSource()
    {
        if(isEnabled>0)
        {
            var intent= Intent(context, SelectStation::class.java)
            intent.putExtra("mode",0)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(context, "Select booking mode to proceed futher", Toast.LENGTH_SHORT).show()
        }
    }
    fun selDest()
    {
        if(isEnabled>0)
        {
            var intent= Intent(context, SelectStation::class.java)
            intent.putExtra("mode",1)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(context, "Select booking mode to proceed futher", Toast.LENGTH_SHORT).show()
        }
    }

fun listeners()
{

    if(!(binding.rb1.isChecked||binding.rb2.isChecked))
    {
        binding.staabr.text = "STN"
        binding.staabr2.text = "STN"
        uts.setMtcurrentSource(Station("STN","Station Name",""))
        uts.setMtcurrentDest(Station("STN","Station Name",""))
        binding.staname.text="Station Name"
        binding.staname2.text="Station Name"
        binding.tempCard.removeAllViews()
    }
    binding.rb2.setOnClickListener {
        binding.rb1.isChecked=false
        isEnabled=2
        uts.save("isEnabled", ""+isEnabled)
        uts.addBookAndPrint(binding.tempCard)
    }
    binding.rb1.setOnClickListener {
        binding.rb2.isChecked=false
        isEnabled=1
        uts.save("isEnabled", ""+isEnabled)
        uts.addBookAndTravel(binding.tempCard)
    }

    binding.sourceLayout.setOnClickListener{
        selSource()
    }
    binding.destLayout.setOnClickListener{
        selDest()
    }
    binding.showTicket.setOnClickListener{
        uts.navigate(context, ShowTicket::class.java)

    }
    binding.btnGetFare.setOnClickListener{
        if(!(uts.getMtcurrentSource().code=="STN"||uts.getMtcurrentDest().code=="STN"))
        {
            uts.navigate(context, DataEntry::class.java)
        }
        else
        {
           uts.showAlertDialog("Enter details first.")
        }


    }



}
    }