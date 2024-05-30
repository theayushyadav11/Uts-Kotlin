package com.example.newuts.activities

import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newuts.R
import com.example.newuts.databinding.ActivityPlatformTicketBinding
import com.example.newuts.utils.Uts

class PlatformTicket : AppCompatActivity() {
    private lateinit var binding:ActivityPlatformTicketBinding
    private lateinit var uts: Uts
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlatformTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uts=Uts(this)
        animateTextView()
        set()
        listeners()
        underline(binding.under1)

    }
    fun animateTextView() {

        val animator = ObjectAnimator.ofFloat(binding.yourTextviewId, "translationX", 600f, -700f)
        animator.duration = 5000
        animator.repeatCount= ObjectAnimator.INFINITE
        animator.start()
    }
    fun set()
    {
        binding.fareticket.text="₹"+uts.getPtFare()
        binding.sourceh.text=uts.getPtStation().nameHindi
        binding.time1.text=uts.getPtTime()
        binding.sourcee.text=uts.getPtStation().nameEnglish
        binding.nextTrains.text="Next Train to "+uts.getPtStation().nameEnglish
        binding.date4.text=uts.getPtDate()
        binding.date2.text=uts.getPtDate()
        binding.phone.text=uts.getPhoneNumber()

        binding.pass.text="No of person(s): ${uts.getPtNop()}"


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
    fun underline(textView:TextView)
    {
        val text = "<u>${textView.text}</u>"
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)

    }
}