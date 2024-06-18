package com.example.cellularautomata.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentContriAboutAppBinding


class ContriAboutAppFragment : Fragment() {
    private lateinit var binding: FragmentContriAboutAppBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentContriAboutAppBinding.inflate(inflater,container,false)
//        val message=activity?.intent?.getStringExtra("message")
//        binding.textDisplay.text=message
        return binding.root
    }
}