package com.example.cellularautomata.HomeFragments

import DataClass.Constants
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentQuizResultBinding


class QuizResultFragment : Fragment() {

    private lateinit var binding: FragmentQuizResultBinding
    private  var correctAnswers=0
    private  var totalQuestions=0
    private  var quizNumber=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentQuizResultBinding.inflate(inflater,container,false)

        correctAnswers = arguments?.getInt(Constants.CORRECT_ANSWER) ?: 0
        totalQuestions = arguments?.getInt(Constants.TOTAL_QUESTION) ?: 0
        quizNumber = arguments?.getInt("quiz_number")?:0

        updateProgressResult()

        binding.btnFinish.setOnClickListener {
            val sharedPreferences=requireActivity().getSharedPreferences(getString(R.string.preference_file),
                AppCompatActivity.MODE_PRIVATE
            )
            sharedPreferences.edit().putInt("quiz$quizNumber",1).apply()
            findNavController().navigate(R.id.action_quizResultFragment_to_quizFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_quizResultFragment_to_quizFragment)
                }
            })

        return binding.root
    }

    private fun updateProgressResult() {

        binding.circularProgressBar.progressMax = totalQuestions.toFloat()
        binding.circularProgressBar.apply {
            setProgressWithAnimation(correctAnswers.toFloat(),(250*correctAnswers).toLong())
        }
        binding.tvScore.text = "Your Score is $correctAnswers/$totalQuestions"

    }
}