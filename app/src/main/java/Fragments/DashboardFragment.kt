package Fragments

import DataClass.Constants
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private var totalCourses = 0
    private var completedCourses = 0
    private var totalQuiz = 0
    private var completedQuiz = 0
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file),
            AppCompatActivity.MODE_PRIVATE
        )

        updateProgressCourse()
        updateProgressQuiz()

        binding.btngotocourse.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_materialFragment)
        }
        binding.btnGoToQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_quizFragment)
        }
        binding.resetButton.setOnClickListener {
            resetCourseAndQuiz()
        }

        return binding.root

    }

    private fun resetCourseAndQuiz() {
        for (i in 0 until Constants.totalCourses) {
            sharedPreferences.edit().putInt("course$i", 0).apply()
        }

        for (i in 0 until Constants.totalQuiz) {
            sharedPreferences.edit().putInt("quiz$i", 0).apply()
        }
        completedCourses = 0
        completedQuiz = 0
        updateProgressCourse()
        updateProgressQuiz()
    }

    private fun updateProgressCourse() {
        totalCourses = Constants.totalCourses

        for (i in 0 until Constants.totalCourses) {
            completedCourses = completedCourses + sharedPreferences.getInt("course$i", 0)
            Log.e("dashboard", "Course complete value : ${sharedPreferences.getInt("course$i", 0)}")
        }

        binding.circularProgressBar.progressMax = totalCourses.toFloat()
//        binding.circularProgressBar.progress = completedCourses.toFloat()
        binding.circularProgressBar.apply {
            setProgressWithAnimation(completedCourses.toFloat(),(300*completedCourses).toLong())
        }
        binding.progressText.text = "Completed $completedCourses out of $totalCourses courses"

    }

    private fun updateProgressQuiz() {
        totalQuiz = Constants.totalQuiz

        for (i in 0 until Constants.totalQuiz) {
            completedQuiz = completedQuiz + sharedPreferences.getInt("quiz$i", 0)
            Log.e("dashboard", "quiz complete value : ${sharedPreferences.getInt("quiz$i", 0)}")
        }


        binding.circularProgressBar2.progressMax = totalQuiz.toFloat()
//        binding.circularProgressBar2.progress = completedQuiz.toFloat()
        binding.circularProgressBar2.apply {
            setProgressWithAnimation(completedQuiz.toFloat(),(250*completedQuiz).toLong())
        }
        binding.progressText2.text = "Completed $completedQuiz out of $totalQuiz quiz"

    }


}