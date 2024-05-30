package com.example.newuts.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import com.example.newuts.R
import com.example.newuts.activities.SelectStation
import com.example.newuts.activities.ShowTicket
import com.example.newuts.databinding.FragmentJourneyTicketBinding
import com.example.newuts.databinding.FragmentPlatformTicketBinding
import com.example.newuts.utils.Uts
import java.util.Calendar


class PlatformTicketFragment : Fragment(R.layout.fragment_platform_ticket) {


    private var _binding: FragmentPlatformTicketBinding? = null
    private val binding get() = _binding!!
    private lateinit var uts: Uts
    private  var nops: String="1"
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlatformTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uts = Uts(context)
        setAdapters()
        setListeners()
        binding.date.text="$day/$month/$year"
        binding.time.text="$hour:$minute"

        binding.ptStation.text=uts.getPtcurrentStation().nameEnglish+"-"+uts.getPtcurrentStation().code
       val isEnabled=uts.get("isEnabled2").toInt()
        if(isEnabled==1)
        {
            binding.rb6.isChecked = false
            binding.rb5.isChecked = true
            binding.tempCard3.removeAllViews()
            uts.addBookAndTravel(binding.tempCard3)
        }
        else if(isEnabled==2)
        {
            binding.rb5.isChecked = false
            binding.rb6.isChecked = true
            binding.tempCard3.removeAllViews()
            uts.addBookAndPrint(binding.tempCard3)
        }







    }

    fun setAdapters() {
        val nop = listOf(1, 2, 3, 4)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                nop
            )
        }
        binding.persons.adapter = adapter
        binding.persons.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    nops = parent.getItemAtPosition(position).toString()
                }
                else
                    nops=""+(position+1)


            }



            override fun onNothingSelected(parent: AdapterView<*>?) {
                nops="1"
            }
        }

    }
    fun setListeners()
    {
        binding.show.setOnClickListener{
            var intent = Intent(context, ShowTicket::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }
        binding.ptStation.setOnClickListener{
            if(uts.get("isEnabled2")!="0") {
                val intent = Intent(context, SelectStation::class.java)
                intent.putExtra("mode", 2)
                startActivity(intent)
            }
            else
            {
                uts.toast("Please select the mode for booking.")
            }
        }
        binding.btnBook.setOnClickListener {
            bookTicket()
        }
        binding.rb5.setOnClickListener {
            binding.rb6.isChecked = false
            uts.save("isEnabled2",""+1)
            binding.tempCard3.removeAllViews()
            uts.addBookAndTravel(binding.tempCard3)
        }
        binding.rb6.setOnClickListener {
            binding.rb5.isChecked = false
            uts.save("isEnabled2",""+2)
            binding.tempCard3.removeAllViews()
            uts.addBookAndPrint(binding.tempCard3)
        }
        binding.ivDate.setOnClickListener{
            showDatePickerDialog()
        }
        binding.ivTime.setOnClickListener{
            showTimePickerDialog()
        }
    }
    fun bookTicket()
    {
        if(uts.get("isEnabled2")!="0") {
        if(!binding.ptStation.text.isNullOrEmpty()&&!binding.fare.text.isNullOrEmpty())
        {

            uts.setPtStation(uts.getPtcurrentStation())
            uts.setPtNop(nops)
            uts.setPtFare(binding.fare.text.toString())
            uts.setPtDate(binding.date.text.toString().uppercase())
            uts.setPtTime(binding.time.text.toString().uppercase())
            var intent = Intent(context, ShowTicket::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }
            else{
                uts.toast("Please enter all the details.")
        }


        }
            else
            {
                uts.toast("Please select the mode for booking.")
            }


    }

    fun showDatePickerDialog() {
        val datePickerDialog = context?.let {
            DatePickerDialog(it, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate =
                    "${format(selectedDay)}/${format(selectedMonth + 1)}/${format(selectedYear)}"
                binding.date.text = "$selectedDate"
            }, year, month, day)
        }
        if (datePickerDialog != null) {
            datePickerDialog.show()
        }
    }
    fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(context, { _: TimePicker, selectedHour: Int, selectedMinute: Int ->

            //  val selectedTime = String.format("%02d:%02d", format(selectedHour), format(selectedMinute))
            binding.time.text = "${format(selectedHour)}:${format(selectedMinute)}"
        }, hour, minute, true)

        timePickerDialog.show()
    }
    fun format(value:Int):String
    {
        if(value<10)
            return "0"+value.toString()
        else
            return value.toString()
    }
}