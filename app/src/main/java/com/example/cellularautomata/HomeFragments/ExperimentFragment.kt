package com.example.cellularautomata.HomeFragments

import adapter.ExperimentListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cellularautomata.databinding.FragmentExperimentBinding

class ExperimentFragment : Fragment() {

    private lateinit var binding: FragmentExperimentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentExperimentBinding.inflate(inflater,container,false)

        val arrayList:ArrayList<String> = arrayListOf(
            "1. Uniform CA\n(no random initial state)",
            "2. Uniform CA\n(random initial state)",
            "3. Uniform CA\n(no random initial state)\n(random position)",
            "4. Uniform CA\n(random initial state)\n(random position)",
            "5. Non Uniform CA\n(random initial state)\n(no random position)",
            "6. Non Uniform CA\n(random initial state)\n(random position)",
            "7. Non Uniform CA\n(random initial state)\n(random position)\n(different rule vector)",
        )

        val adapter= ExperimentListAdapter(requireContext(),arrayList)
        binding.recylerView.layoutManager= StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.recylerView.adapter=adapter

        return binding.root
    }

}