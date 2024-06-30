package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cellularautomata.databinding.FragmentContributorBinding


class ContributorFragment : Fragment() {
    private lateinit var binding: FragmentContributorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentContributorBinding.inflate(inflater,container,false)
        return binding.root
    }
}