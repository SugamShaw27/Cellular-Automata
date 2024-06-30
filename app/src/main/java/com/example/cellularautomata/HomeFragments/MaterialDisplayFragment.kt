package com.example.cellularautomata.HomeFragments

import DataClass.Constants
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentMaterialDisplayBinding


class MaterialDisplayFragment : Fragment() {
    private lateinit var binding: FragmentMaterialDisplayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMaterialDisplayBinding.inflate(inflater, container, false)

        val title = arguments?.getString("title")
        val data = arguments?.getString("data").toString()
        val materialNumber = arguments?.getInt("material_number")

        binding.textShow.text = data

        binding.ytLink1.setOnClickListener {
            openUrl("https://youtu.be/X119QcvOGOw?si=FWhOZFt8lvqR0IGi")
        }
        binding.ytLink2.setOnClickListener {
            openUrl("https://www.youtube.com/live/Mr7uFkKp3j0?si=YXQ-HJvUZQm61rHm")
        }
        binding.ytLink3.setOnClickListener {
            openUrl("https://youtu.be/xT3m3kpA_8Q?si=Vt9J2QeMwiNMIqek")
        }
        binding.ytLink4.setOnClickListener {
            openUrl("https://en.wikipedia.org/wiki/Cellular_automaton")
        }

        binding.buttonComplete.setOnClickListener {
            Toast.makeText(requireContext(), "Course Completed", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(requireContext(), "Take Quiz", Toast.LENGTH_SHORT).show()
            }, 1000)
            val sharedPreferences=requireActivity().getSharedPreferences(getString(R.string.preference_file),
                AppCompatActivity.MODE_PRIVATE
            )
            sharedPreferences.edit().putInt("course$materialNumber",1).apply()
        }

        binding.buttonQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_materialDisplayFragment_to_quizFragment)
        }

        return binding.root
    }

    private fun openUrl(str: String) {
        val url = Uri.parse(str)
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)

    }

}