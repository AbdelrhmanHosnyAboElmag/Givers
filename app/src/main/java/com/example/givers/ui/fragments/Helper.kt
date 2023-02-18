package com.example.givers.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.givers.databinding.FragmentHelperBinding


class Helper : Fragment() {
    private lateinit var binding: FragmentHelperBinding
    private val pic_id = 123


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelperBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.ivItem.setOnClickListener {
            val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera_intent, pic_id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === pic_id) {
            val photo: Bitmap? = data!!.extras!!["data"] as Bitmap?
            binding.ivItem.setImageBitmap(photo)
        }
    }

}