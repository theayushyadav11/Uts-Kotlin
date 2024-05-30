package com.example.newuts.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newuts.databinding.ActivityProfileBinding
import com.example.newuts.utils.Uts

class Profile : AppCompatActivity() {
    private lateinit var binding:ActivityProfileBinding
    private lateinit var uts: Uts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uts=Uts(this)
        binding.login.setOnClickListener {
            if (binding.phone.text.toString().length < 10) {
                uts.toast("Invalid phone number!")
            } else {
                uts.setPhoneNumber(binding.phone.text.toString())
                uts.navigate(this@Profile,MainActivity::class.java)
            }
        }

    }
}