package com.example.newuts.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.newuts.databinding.FragmentQrBookingBinding
import com.example.newuts.utils.Uts


class QrBookingFragment : Fragment() {
    private var _binding: FragmentQrBookingBinding? = null
    private val binding get() = _binding!!
    private lateinit var uts: Uts
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQrBookingBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            listeners()

    }
    fun listeners()
    {
        binding.jt.setOnClickListener {
            if (checkPermissions()) {
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(intent)

            } else {
                requestPermissions()
            }

        }

        binding.pt.setOnClickListener { if (checkPermissions()) {
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)

        } else {
            requestPermissions()
        }

        }
        binding.ss.setOnClickListener {
            if (checkPermissions()) {

                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(intent)
            } else {
                requestPermissions()
            }

        }
    }
    private fun checkPermissions(): Boolean {
        val cameraPermission = context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (context?.let { takePictureIntent.resolveActivity(it.packageManager) } != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(context, "No camera app found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(context, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

}