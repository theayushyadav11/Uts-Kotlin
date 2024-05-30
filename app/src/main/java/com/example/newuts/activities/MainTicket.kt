package com.example.newuts.activities

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.ProgressDialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.newuts.databinding.ActivityMainTicketBinding
import com.example.newuts.utils.Uts
import java.util.logging.Handler

class MainTicket : AppCompatActivity() {
    lateinit var binding:ActivityMainTicketBinding
    lateinit var uts:Uts
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uts=Uts(this)
        animateTextView()
        set()
        listeners()

    }

    fun set()
    {
     binding.date2.text=uts.getMtDate().uppercase()
     binding.fareticket.text="₹ "+uts.getMtFare().uppercase()
     binding.nextTrains.text="NEXT TRAINS TO ${uts.getMtDest().nameEnglish}"
     binding.sourceh.text=uts.getMtSource().nameHindi.uppercase()
     binding.sourcee.text=uts.getMtSource().nameEnglish.uppercase()
     binding.deste.text=uts.getMtDest().nameEnglish.uppercase()
     binding.desth.text=uts.getMtDest().nameHindi.uppercase()
     binding.pass.text="ADULT:${uts.getMtNop()}   CHILD:0"
     binding.typeHindi.text=uts.getMtTrainTypeHindi()
     binding.typeEnglish.text=uts.getMtTrainTypeEng()
     binding.via.text=uts.getMtVia()
     binding.date3.text=uts.getMtDate().uppercase()
     binding.distanceticket.text="Distance : ${uts.getMtDistance()} km"
     binding.date4.text=uts.getMtDate().uppercase()
     binding.time1.text=uts.getMtTime().uppercase()
     binding.phone.text=uts.getPhoneNumber()
        PlatformTicket().underline(binding.under1)
            }
    fun listeners()
    {
        binding.ok.setOnClickListener{
            uts.navigate(this, MainActivity::class.java)
        }
        binding.qr.setOnClickListener{

                showProgressDialog()

                android.os.Handler().postDelayed({
                    dismissProgressDialog()
                }, 4000)
            }

    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading QR Code")
            setCancelable(false)
            show()
        }
    }

    private fun dismissProgressDialog() {
        if (this::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
            uts.toast("Failed to load QR ,Check Internet Connection!")
        }
    }
    fun animateTextView() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val translateX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X,  (screenWidth - binding.yourTextviewId.width).toFloat(),-(screenWidth - binding.yourTextviewId.width).toFloat())
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.yourTextviewId, translateX)
        animator.duration = 5500
        animator.repeatCount=ObjectAnimator.INFINITE
        animator.start()
    }
}