package com.example.cellularautomata.HomeFragments

import adapter.QuizAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentQuizBinding.inflate(inflater,container,false)

        val array:ArrayList<String> = arrayListOf("Quiz 1","Quiz 2","Quiz 3","Quiz 4","Quiz 5","Quiz 6","Quiz 7","Quiz 8","Quiz 9","Quiz 10")
        val adapter= QuizAdapter(requireContext(),array)
        binding.recylerView.layoutManager= StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.recylerView.adapter=adapter

        return binding.root
    }
}