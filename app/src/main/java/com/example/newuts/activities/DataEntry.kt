package com.example.newuts.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.newuts.databinding.ActivityDataEntryBinding
import com.example.newuts.utils.Uts
import java.util.Calendar

class DataEntry : AppCompatActivity() {
    private lateinit var binding:ActivityDataEntryBinding
    private lateinit var uts: Uts
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDataEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uts=Uts(this)
        binding.date.text="${format(day)}/${format(month)}/${format(year)}"
        binding.time.text="${format(hour)}:${format(minute)}"
        setAdapters()
        set()
        binding.ivDate.setOnClickListener{
            showDatePickerDialog()
        }
        binding.ivTime.setOnClickListener{
            showTimePickerDialog()
        }
        binding.button6.setOnClickListener{
            done()
        }

    }
    fun setAdapters()
    {
        val nop= listOf(1,2,3,4)
        val adapter=ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,nop)
        binding.nopmain.adapter=adapter
        binding.nopmain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                uts.setMtNop(selectedItem)

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                uts.setMtNop("1")
            }
        }

        val type= listOf("Mail/Express","Superfast")
        val adapterType=ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,type)
        binding.type.adapter=adapterType
        binding.type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString().uppercase()
                uts.setMtTrainTypeEng(selectedItem)
                if(position==0)
                {
                    uts.setMtTrainTypeHindi("मेल/एक्सप्रेस")
                }
                else
                {
                    uts.setMtTrainTypeHindi("सुपरफास्ट")
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                uts.setMtTrainTypeEng("SUPERFAST")
            }
        }



    }
    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "${format(selectedDay)}/${format(selectedMonth + 1)}/${format(selectedYear)}"
            binding.date.text = "$selectedDate"
        }, year, month, day)
        datePickerDialog.show()
    }
fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(this, { _: TimePicker, selectedHour: Int, selectedMinute: Int ->

          //  val selectedTime = String.format("%02d:%02d", format(selectedHour), format(selectedMinute))
            binding.time.text = "${format(selectedHour)}:${format(selectedMinute)}"
        }, hour, minute, true)

        timePickerDialog.show()
    }
    fun done()
    {
        uts.setMtFare(binding.faremain.text.toString().uppercase())
        uts.setMtDistance(binding.distancemain.text.toString().uppercase())
        uts.setMtVia(binding.viamain.text.toString().uppercase())
        uts.setMtDate(binding.date.text.toString().uppercase())
        uts.setMtTime(binding.time.text.toString().uppercase())
        uts.setMtDest(uts.getMtcurrentDest())
        uts.setMtSource(uts.getMtcurrentSource())
        uts.navigate(this, ShowTicket::class.java)
    }
    fun format(value:Int):String
    {
        if(value<10)
            return "0"+value.toString()
        else
            return value.toString()
    }
    fun set()
    {
        var  p=(uts.getMtFare().substring(0,uts.getMtFare().indexOf("."))).toInt()/uts.getMtNop().toInt()
        binding.faremain.setText(p.toString())
        binding.distancemain.setText(uts.getMtDistance())
        binding.distancemain.setText(uts.getMtDistance())
        if(uts.getMtVia()=="")
        {
            binding.viamain.setText("-")
        }
        else
        {
            binding.viamain.setText(uts.getMtVia())
        }

    }
}