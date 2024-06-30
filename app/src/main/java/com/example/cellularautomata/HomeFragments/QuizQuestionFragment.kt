package com.example.cellularautomata.HomeFragments

import DataClass.Constants
import DataClass.Question

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentQuizQuestionBinding

class QuizQuestionFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentQuizQuestionBinding

    private var mcurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedoptionPosition: Int = 0
    private var mCorrectAnswer: Int = 0

    private lateinit var tvQuestions: TextView
    private lateinit var ivImage: ImageView
    private lateinit var progressbar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var btnSubmit: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        setListeners()

        mQuestionList = Constants.getQuestions()
        binding.progressbar.max = mQuestionList!!.size
        setQuestion()
        defaultOptionView()
    }

    private fun initializeViews() {
            tvQuestions = binding.tvQuestions
            ivImage = binding.ivImage
            progressbar = binding.progressbar
            tvProgress = binding.tvProgress
            tvOptionOne = binding.tvOptionOne
            tvOptionTwo = binding.tvOptionTwo
            tvOptionThree = binding.tvOptionThree
            tvOptionFour = binding.tvOptionFour
            btnSubmit = binding.btnSubmit
    }


    private fun setListeners() {
        with(binding) {
            tvOptionOne.setOnClickListener(this@QuizQuestionFragment)
            tvOptionTwo.setOnClickListener(this@QuizQuestionFragment)
            tvOptionThree.setOnClickListener(this@QuizQuestionFragment)
            tvOptionFour.setOnClickListener(this@QuizQuestionFragment)
            btnSubmit.setOnClickListener(this@QuizQuestionFragment)
        }
    }

    private fun setQuestion() {
        defaultOptionView()

        val question: Question = mQuestionList!![mcurrentPosition - 1]

        with(binding) {
            if (question.image != null) {
                ivImage.setImageResource(question.image)
            } else {
                ivImage.visibility = View.GONE
            }

            progressbar.progress = mcurrentPosition
            tvProgress.text = "$mcurrentPosition/${mQuestionList!!.size}"
            tvQuestions.text = question.question
            tvOptionOne.text = question.optionOne
            tvOptionTwo.text = question.optionTwo
            tvOptionThree.text = question.optionThree
            tvOptionFour.text = question.optionFour

            if (mcurrentPosition == mQuestionList!!.size) {
                btnSubmit.text = "FINISH"
            } else {
                btnSubmit.text = "Submit"
            }
        }
    }

    private fun defaultOptionView() {
        val options = listOf(
            binding.tvOptionOne,
            binding.tvOptionTwo,
            binding.tvOptionThree,
            binding.tvOptionFour
        )

        options.forEach { option ->
            option.setTextColor(Color.parseColor("#7A8089"))
            option.setTypeface(Typeface.DEFAULT)
            option.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        mSelectedoptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.tvOptionOne.id -> selectedOptionView(binding.tvOptionOne, 1)
            binding.tvOptionTwo.id -> selectedOptionView(binding.tvOptionTwo, 2)
            binding.tvOptionThree.id -> selectedOptionView(binding.tvOptionThree, 3)
            binding.tvOptionFour.id -> selectedOptionView(binding.tvOptionFour, 4)
            binding.btnSubmit.id -> {
                if (mSelectedoptionPosition == 0) {
                    mcurrentPosition++
                    if (mcurrentPosition <= mQuestionList!!.size) {
                        setQuestion()
                    } else {
                        val quizNumber = arguments?.getInt("quiz_number")

                        val bundle = Bundle()
                        bundle.putInt(Constants.CORRECT_ANSWER, mCorrectAnswer)
                        bundle.putInt(Constants.TOTAL_QUESTION, mQuestionList!!.size)
                        bundle.putInt("quiz_number", quizNumber!!)
                        findNavController().navigate(R.id.action_quizQuestionFragment_to_quizResultFragment,bundle)
                    }
                } else {
                    val question1 = mQuestionList?.get(mcurrentPosition - 1)

                    if (question1!!.correctAnswer != mSelectedoptionPosition) {
                        answerView(mSelectedoptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswer++
                    }
                    answerView(question1.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mcurrentPosition == mQuestionList!!.size) {
                        binding.btnSubmit.text = "FINISH"
                    } else {
                        binding.btnSubmit.text = "Go to next question"
                    }
                    mSelectedoptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.tvOptionOne.background =
                ContextCompat.getDrawable(requireContext(), drawableView)

            2 -> binding.tvOptionTwo.background =
                ContextCompat.getDrawable(requireContext(), drawableView)

            3 -> binding.tvOptionThree.background =
                ContextCompat.getDrawable(requireContext(), drawableView)

            4 -> binding.tvOptionFour.background =
                ContextCompat.getDrawable(requireContext(), drawableView)
        }
    }
}
