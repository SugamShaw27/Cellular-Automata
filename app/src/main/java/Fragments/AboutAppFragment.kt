package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentAboutAppBinding
import com.example.cellularautomata.databinding.FragmentPlayGamesDisplayBinding


class AboutAppFragment : Fragment() {
    private lateinit var binding: FragmentAboutAppBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAboutAppBinding.inflate(inflater,container,false)


        return binding.root
    }

}