package com.example.cellularautomata.HomeFragments

import adapter.GameoflifeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentPlayGamesBinding

class PlayGamesFragment : Fragment() {

    private lateinit var  binding: FragmentPlayGamesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPlayGamesBinding.inflate(inflater,container,false)

        var array: ArrayList<String> = arrayListOf(
            "1. Game of Life",
            "2. Majority rule xor vonneumann",
            "3. Majority rule xor moore_rule",
            "4. SumMod2 moore  rule",
            "5. SumMod2 vonneumann",
        )
        val adapter = GameoflifeAdapter(requireContext(),array)
        binding.recylerView.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recylerView.adapter=adapter


        return binding.root
    }

}