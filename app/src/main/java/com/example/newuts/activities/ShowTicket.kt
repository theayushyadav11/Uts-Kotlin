package com.example.newuts.activities

import android.net.wifi.p2p.WifiP2pManager.PeerListListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newuts.databinding.ActivityShowTicketBinding
import com.example.newuts.utils.Uts


class ShowTicket : AppCompatActivity() {
    lateinit var binding: ActivityShowTicketBinding
    lateinit var uts:Uts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowTicketBinding.inflate(layoutInflater)

        setContentView(binding.root)
        uts = Uts(this)
        uts.save("isEnabled", "" + 0)
        set()
        binding.vt.setOnClickListener{
            uts.navigate(this@ShowTicket, MainTicket::class.java)
        }
        binding.viewPtTicket.setOnClickListener{
            uts.navigate(this@ShowTicket,PlatformTicket::class.java)
        }
  if(uts.getMtSource().code=="STN")
  {
      binding.showTicket.isVisible=false
     binding.message.isVisible=true
  }
if(intent.getIntExtra("type",0)==1)
{
    binding.showTicket.isVisible=false
    binding.platTicket.isVisible=true
    setPlatform()
    if(uts.getPtStation().code=="STN")
    {
        binding.showTicket.isVisible=false
        binding.platTicket.isVisible=false
        binding.message.isVisible=true
    }
    else
    {
        binding.message.isVisible=false
    }
}
    }
    fun back(v: View)
    {
        onBackPressed()
    }

    fun set()
    {
        binding.source.text=uts.getMtSource().nameEnglish
        binding.dest.text=uts.getMtDest().nameEnglish
        binding.fareprice.text=uts.getMtFare()
        binding.via.text=uts.getMtVia()
        binding.nop.text=uts.getMtNop()
        binding.traintype.text=uts.getMtTrainTypeEng()
        binding.date1.text=uts.getMtDate()+"  "+uts.getMtTime()
    }
    fun setPlatform()
    {
        binding.farepriceplat.text=uts.getPtFare()
        binding.sourceplat.text=uts.getPtStation().nameEnglish
        binding.nopplat.text=uts.getPtNop()
        binding.date1plat.text=uts.getPtDate()
        binding.time1plat.text=uts.getPtTime()

    }
}