package com.example.newuts.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.newuts.R
import com.example.newuts.databinding.FragmentPlatformTicketBinding
import java.util.Calendar

class Uts {
    lateinit var sharedPreferences: SharedPreferences
   lateinit var inflater :LayoutInflater
   lateinit var context :Context

  constructor(context: Context?)
    {
       sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context)
       inflater= LayoutInflater.from(context)
        if (context != null) {
            this.context=context
        }
  }
  fun  toast(message:String)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun save(key: String, value:String)
    {
              val editor=sharedPreferences.edit();
        editor.putString(key,value)
        editor.apply()
    }
    fun get(key: String):String
    {
        return sharedPreferences.getString(key,"").toString()
    }
    fun navigate(from:Context?, to: Class<*>)
    {
        val intent= Intent(from,to)
        from?.startActivity(intent)
    }
    fun addBookAndTravel(parent:LinearLayout)
    {
        parent.removeAllViews()
        val customView = inflater.inflate(R.layout.book_and_travel, parent, false)
        parent.addView(customView)

    }
    fun addBookAndPrint(parent:LinearLayout)
    {
         parent.removeAllViews()
        val customView = inflater.inflate(R.layout.book_and_print, parent, false)
        parent.addView(customView)

    }
    fun addLocalStation(station:Station)
    {
        var stringStation=get("local_stations")
        stringStation=station.code+"*" + station.nameEnglish+"*" + station.nameHindi +"#"+stringStation
        save("local_stations", stringStation)


    }
    fun remove()
    {
        val editor=sharedPreferences.edit()
        editor.remove("local_stations")
        editor.apply()
    }
    fun getLocalStations():ArrayList<Station>
    {
        var stringStation=get("local_stations")
        var v:ArrayList<Station> = arrayListOf()
        var arrayOfStations=stringStation.split("#")
        println(arrayOfStations)
        for(i in 0 until arrayOfStations.size-1)
        {
            var temp=arrayOfStations[i].split("*")
            var station=Station(temp[0], temp[1], temp[2])
            v.add(station)
        }
        return v

    }
   fun removeStation(station:Station)
   {
       try {
           var stringStation=""
           var stationList=getLocalStations()
           stationList.remove(station)
           remove()
           for(i in stationList)
           {
               stringStation+=i.code+"*" + i.nameEnglish+"*" + i.nameHindi +"#"

           }
           save("local_stations", stringStation)
       } catch (e: Exception) {

       }

   }
    //Date
    fun setMtDate(date:String)
    {
        save("mtdate",date)
    }
    fun getMtDate():String{
        return get("mtdate")
    }

    //Fare
    fun setMtFare(s:String)
    {
        save("mtfare","${s.toInt()*getMtNop().toInt()}.00")
    }
    fun getMtFare():String{
        return get("mtfare")
    }

    //Source
    fun setMtSource(station:Station)
    {
       
        save("mtSource",station.code+"*"+station.nameEnglish+"*"+station.nameHindi)
    }
    fun getMtSource():Station{
     
        val v= get("mtSource")
        var array=v.split("*")
        if(array.size>=2)
        return  Station(array[0],array[1],array[2])
        else
            return Station("STN","Station Name","Station Name")
    }


    //Dest
    fun setMtDest(station:Station)
    {

        save("mtDest",station.code+"*"+station.nameEnglish+"*"+station.nameHindi)
    }
    fun getMtDest():Station{

        val v= get("mtDest")
        var array=v.split("*")
        if(array.size>=2)
            return  Station(array[0],array[1],array[2])
        else
            return Station("STN","Station Name","Station Name")
    }


    //Nop
    fun setMtNop(s:String)
    {
        save("mtNop",s)
    }
    fun getMtNop():String{
        return get("mtNop")
    }

  
    //TrainTypeHindi
    fun setMtTrainTypeHindi(s:String)
    {
        save("mtTrainTypeHindi",s)
    }
    fun getMtTrainTypeHindi():String{
        return get("mtTrainTypeHindi")
    }

    //TrainTypeEng
    fun setMtTrainTypeEng(s:String)
    {
        save("mtTrainTypeEng",s)
    }
    fun getMtTrainTypeEng():String{
        return get("mtTrainTypeEng")
    }

    //Distance
    fun setMtDistance(s:String)
    {
        save("mtDistance",s)
    }
    fun getMtDistance():String{
        return get("mtDistance")
    }

    //Time
    fun setMtTime(s:String)
    {
        save("mtTime",s)
    }
    fun getMtTime():String{
        return get("mtTime")
    }
    //Via
    fun setMtVia(s:String)
    {
        save("mtVia",s)
    }
    fun getMtVia():String{
        return get("mtVia")
    }


    //currentSource
    fun setMtcurrentSource(station:Station)
    {
        save("mtcurrentSource",station.code+"*"+station.nameEnglish+"*"+station.nameHindi)
    }
    fun getMtcurrentSource():Station{
        val v= get("mtcurrentSource")
        var array=v.split("*")
        return  Station(array[0],array[1],array[2])

    }

    //currentDest
    fun setMtcurrentDest(station:Station)
    {
        save("mtcurrentDest",station.code+"*"+station.nameEnglish+"*"+station.nameHindi)
    }
    fun getMtcurrentDest():Station{
        val v= get("mtcurrentDest")
        var array=v.split("*")
        return  Station(array[0],array[1],array[2])

    }
 fun showAlertDialog(message:String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Alert!")
        builder.setMessage(message)

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun setHasMainTicket(b:Boolean)
    {
        save("hasMainTicket",""+b)
    }

    fun hasMainTicket():Boolean
    {
        return get("hasMainTicket").toBooleanStrict()
    }
    fun setPtStation(station:Station)
    {
        save("ptStation",station.code+"*"+station.nameEnglish+"*"+station.nameHindi)
    }
    fun getPtStation():Station{

        val v= get("ptStation")
        var array=v.split("*")
        if(array.size>=2)
            return  Station(array[0],array[1],array[2])
        else
            return Station("STN","Station Name","Station Name")
    }
    fun setPtNop(s:String)
    {
        save("ptNop",s)
    }
    fun getPtNop():String{
        return get("ptNop")
    }
    fun setPtFare(s:String)
    {
        save("ptfare","${s.toInt()*getPtNop().toInt()}.00")
    }
    fun getPtFare():String{
        return get("ptfare")
    }
    fun setPtcurrentStation(station:Station)
    {
        save("ptcurrentStation",station.code+"*"+station.nameEnglish+"*"+station.nameHindi)
    }
    fun getPtcurrentStation():Station{
        val v= get("ptcurrentStation")
        var array=v.split("*")
        return  Station(array[0],array[1],array[2])

    }
    fun setPtDate(s:String)
    {
        save("ptDate",s)
    }
    fun getPtDate():String{
        return get("ptDate")
    }
    fun setPtTime(s:String)
    {
        save("ptTime",s)
    }
    fun getPtTime():String{
        return get("ptTime")
    }
    fun getCurrentDate():String{
        val calender =Calendar.getInstance()
        return calender.get(Calendar.DATE).toString()+"/"+calender.get(Calendar.MONTH)+"/"+calender.get(Calendar.YEAR)
    }
   fun getCurrentTime():String{
       val calender =Calendar.getInstance()
       return calender.get(Calendar.HOUR_OF_DAY).toString()+":"+calender.get(Calendar.MINUTE).toString()
   }
   fun setPhoneNumber(phoneNumber:String)
   {
       save("phoneNumber", phoneNumber)
   }
    fun getPhoneNumber():String
    {
        return if (get("phoneNumber").isNullOrEmpty()) "9696620395" else get("phoneNumber")
    }
}