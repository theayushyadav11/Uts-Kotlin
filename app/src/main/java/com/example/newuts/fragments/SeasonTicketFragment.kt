package com.example.newuts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newuts.R
import com.example.newuts.databinding.FragmentSeasonTicketBinding
import com.example.newuts.utils.Uts


class SeasonTicketFragment : Fragment() {
       private var _binding:FragmentSeasonTicketBinding?=null
    private val binding get() = _binding!!
    lateinit var uts:Uts


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentSeasonTicketBinding.inflate(inflater,container,false)

        uts=Uts(context)


        binding.rb3.setOnClickListener {
            binding.rb4.isChecked = false
            uts.save("isEnabled3",""+1)
            binding.bat3.removeAllViews()
            uts.addBookAndTravel(binding.bat3)
        }
        binding.rb4.setOnClickListener {
            binding.rb3.isChecked = false
            uts.save("isEnabled3",""+2)
            binding.bat3.removeAllViews()
            uts.addBookAndPrint(binding.bat3)
        }
        return binding.root
    }



}