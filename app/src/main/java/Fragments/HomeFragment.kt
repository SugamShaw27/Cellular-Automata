package Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.cellularautomata.GameoflifeActivity
import com.example.cellularautomata.PlayActivity
import com.example.cellularautomata.StudyActivity
import com.example.cellularautomata.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()
        binding.imgstudy.setOnClickListener {
            val intent = Intent(requireContext(), StudyActivity::class.java)
            startActivity(intent)
        }
        binding.imgplay.setOnClickListener {
            val intent = Intent(requireContext(), PlayActivity::class.java)
            startActivity(intent)
        }
        binding.imgGameofLife.setOnClickListener {
            val intent = Intent(requireContext(), GameoflifeActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}