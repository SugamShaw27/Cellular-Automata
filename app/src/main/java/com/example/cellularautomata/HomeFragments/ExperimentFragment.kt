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
            "1. Uniform CA\n(no random initial state)\n(final_code)", //input-> rule no. , return -> img,png
            "2. Uniform CA\n(random initial state)\n(final_code)",    //input-> rule no.
            "3. Uniform CA\n(no random initial state)\n(random position)\n(final_code2)", //input-> rule no.
            "4. Uniform CA\n(random initial state)\n(random position)\n(final_code2)",    //input-> rule no.
            "5. Non Uniform CA\n(random initial state)\n(no random position)\n(final_code4)",
            "6. Non Uniform CA\n(random initial state)\n(random position)\n(final_code4)",
        )

        val adapter= ExperimentListAdapter(requireContext(),arrayList)
        binding.recylerView.layoutManager= StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.recylerView.adapter=adapter

        return binding.root
    }

}