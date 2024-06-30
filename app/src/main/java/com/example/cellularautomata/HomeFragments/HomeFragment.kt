package com.example.cellularautomata.HomeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()
        binding.imgMaterial.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_materialFragment)
        }
        binding.imgexperiment.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_experimentFragment)
        }
        binding.imgPlayGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_playGamesFragment)
        }

        binding.imgQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        return binding.root
    }
    

}