package com.example.cellularautomata.HomeFragments

import Views.GameOfLifeView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.FragmentPlayGamesDisplayBinding

class PlayGamesDisplayFragment : Fragment() {

    private lateinit var binding: FragmentPlayGamesDisplayBinding
    private var gameNumber: Int = 0
    private lateinit var gameName: String
    private var isRunninggameoflife = false
    private lateinit var gameFunctionName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Python with AndroidPlatform
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(requireContext()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayGamesDisplayBinding.inflate(inflater, container, false)

        gameNumber = arguments?.getInt("game_number") ?: 0
        gameNumber += 1
        gameName = arguments?.getString("game_name") ?: ""

        binding.gameName.text = gameName

        when (gameNumber) {
            1 -> {
                gameFunctionName = "apply_game_of_life_rule"
                rungame("The Game of Life is a cellular automaton created by John Conway in 1970. It consists of a grid where each cell is either alive or dead, evolving based on these rules:\n1. Any live cell with fewer than two live neighbors dies (underpopulation).\n2. Any live cell with two or three live neighbors lives on (survival).\n3. Any live cell with more than three live neighbors dies (overpopulation).\n4. Any dead cell with exactly three live neighbors becomes a live cell (reproduction).\n5. Alive Neighbor Count: The number of alive neighbors is calculated by summing the values in the 3x3 neighborhood and subtracting the cell's own value.\nRule Application:\n" +
                        "1. If alive (grid[i, j] == 1): Dies if fewer than 2 or more than 3 neighbors.\nLives if 2 or 3 neighbors.\n" +
                        "2. If dead (grid[i, j] == 0): Becomes alive if exactly 3 neighbors.")
            }

            2 -> {
                gameFunctionName = "apply_majority_rule_xor_von_neumann"
                rungame("The apply_majority_rule_xor_von_neumann function simulates a cellular automaton where each cell's state is updated based on the XOR majority rule of its Von Neumann neighborhood. Here's a description:\n" +
                        "This function processes a 2D grid where each cell can be either 0 (dead) or 1 (alive). It applies a rule where each cell's new state is determined by the majority vote of its neighbors: north, west, east, and south. Specifically, for each cell at position (i, j) in the grid:\n" +
                        "1. The function computes the XOR (exclusive OR) operation between the cell's own value and its neighboring cells' values in the Von Neumann neighborhood.\n" +
                        "2. The resulting value determines whether the cell becomes alive (1) or stays dead (0) in the next generation.\n" +
                        "3. The grid is processed with wrap-around edges, meaning cells at the grid's edges consider neighbors on the opposite side of the grid.")
            }
            3-> {
                gameFunctionName = "apply_majority_rule_xor_moore_rule"
                rungame("The apply_majority_rule_xor_moore_rule function simulates a cellular automaton where each cell's state is updated based on the XOR majority rule of its Moore neighborhood. Here's a description of how the function works:\n" +
                        "This function processes a 2D grid where each cell can be either 0 (dead) or 1 (alive). It checks the shape of the grid to ensure it's 2D. For each cell at position (i, j) in the grid:\n" +
                        "1. It constructs a 3x3 neighborhood around the cell using wrapping around the edges to handle boundary conditions.\n" +
                        "2. It computes the XOR (exclusive OR) operation on all cells in the neighborhood, except the central cell itself.\n" +
                        "3. The resulting XOR value determines the new state of the cell: 1 (alive) if the XOR is non-zero, and 0 (dead) if the XOR is zero.\n" +
                        "4. The function then updates the entire grid based on these computations and returns the updated grid as a list of lists.")
            }
            4-> {
                gameFunctionName = "apply_sum_mod_2_xor_moore_rule"
                rungame("The apply_sum_mod_2_xor_moore_rule function implements a cellular automaton rule where each cell's state is updated based on the sum modulo 2 of its Moore neighborhood. Here's a description of how the function works:\n" +
                        "This function processes a 2D grid where each cell can be either 0 (dead) or 1 (alive). It first converts the input grid into a NumPy array and checks its dimensions to ensure it's 2D.\n" +
                        "For each cell at position (i, j) in the grid:\n" +
                        "1. It constructs a 3x3 neighborhood around the cell using wrap-around edges to handle boundary conditions.\n" +
                        "2. It calculates the sum of all values in the neighborhood, including the cell's own value.\n" +
                        "3. The modulo 2 operation (% 2) is applied to the sum. This operation results in 1 if the sum is odd (indicating an odd number of alive neighbors) and 0 if the sum is even (indicating an even number of alive neighbors).\n" +
                        "4. The resulting value determines the new state of the cell in the next generation.\n" +
                        "5. The function updates the entire grid based on these computations and returns the updated grid as a list of lists.")
            }
            5-> {
                gameFunctionName = "apply_sum_mod_2_xor_von_neumann"
                rungame("The apply_sum_mod_2_xor_von_neumann function implements a cellular automaton rule where each cell's state is updated based on the sum modulo 2 of its Von Neumann neighborhood. Here's a concise description:\n" +
                        "This function processes a 2D grid where each cell can be either 0 (dead) or 1 (alive). It first converts the input grid into a NumPy array and checks its dimensions to ensure it's 2D.\n" +
                        "For each cell at position (i, j) in the grid:\n" +
                        "1. It constructs the Von Neumann neighborhood, which includes cells to the north, west, east, and south, handling wrap-around edges to ensure boundary conditions are met.\n" +
                        "2. It calculates the sum of the cell's own value and its neighboring values.\n" +
                        "3. The modulo 2 operation (% 2) is applied to the sum. This operation results in 1 if the sum is odd (indicating an odd number of alive neighbors) and 0 if the sum is even (indicating an even number of alive neighbors).\n" +
                        "4. The resulting value determines the new state of the cell in the next generation.\n" +
                        "5. The function updates the entire grid based on these computations and returns the updated grid as a list of lists.")
            }
            6 -> {
                gameFunctionName = "apply_game_of_life_rule_glider"
                rungame("Glider : The Game of Life is a cellular automaton created by John Conway in 1970. It consists of a grid where each cell is either alive or dead, evolving based on these rules:\n1. Any live cell with fewer than two live neighbors dies (underpopulation).\n2. Any live cell with two or three live neighbors lives on (survival).\n3. Any live cell with more than three live neighbors dies (overpopulation).\n4. Any dead cell with exactly three live neighbors becomes a live cell (reproduction).\n5. Alive Neighbor Count: The number of alive neighbors is calculated by summing the values in the 3x3 neighborhood and subtracting the cell's own value.\nRule Application:\n" +
                        "1. If alive (grid[i, j] == 1): Dies if fewer than 2 or more than 3 neighbors.\nLives if 2 or 3 neighbors.\n" +
                        "2. If dead (grid[i, j] == 0): Becomes alive if exactly 3 neighbors.")
            }
            7 -> {
                gameFunctionName = "apply_game_of_life_rule_pulsar"
                rungame("Glider : The Game of Life is a cellular automaton created by John Conway in 1970. It consists of a grid where each cell is either alive or dead, evolving based on these rules:\n1. Any live cell with fewer than two live neighbors dies (underpopulation).\n2. Any live cell with two or three live neighbors lives on (survival).\n3. Any live cell with more than three live neighbors dies (overpopulation).\n4. Any dead cell with exactly three live neighbors becomes a live cell (reproduction).\n5. Alive Neighbor Count: The number of alive neighbors is calculated by summing the values in the 3x3 neighborhood and subtracting the cell's own value.\nRule Application:\n" +
                        "1. If alive (grid[i, j] == 1): Dies if fewer than 2 or more than 3 neighbors.\nLives if 2 or 3 neighbors.\n" +
                        "2. If dead (grid[i, j] == 0): Becomes alive if exactly 3 neighbors.")
            }
            8 -> {
                gameFunctionName = "apply_game_of_life_rule_glider_gun"
                rungame("Glider : The Game of Life is a cellular automaton created by John Conway in 1970. It consists of a grid where each cell is either alive or dead, evolving based on these rules:\n1. Any live cell with fewer than two live neighbors dies (underpopulation).\n2. Any live cell with two or three live neighbors lives on (survival).\n3. Any live cell with more than three live neighbors dies (overpopulation).\n4. Any dead cell with exactly three live neighbors becomes a live cell (reproduction).\n5. Alive Neighbor Count: The number of alive neighbors is calculated by summing the values in the 3x3 neighborhood and subtracting the cell's own value.\nRule Application:\n" +
                        "1. If alive (grid[i, j] == 1): Dies if fewer than 2 or more than 3 neighbors.\nLives if 2 or 3 neighbors.\n" +
                        "2. If dead (grid[i, j] == 0): Becomes alive if exactly 3 neighbors.")
            }
            else -> {
                gameFunctionName = "apply_game_of_life_rule"
                rungame("Game of life")
            }
        }
        binding.gameOfLifeView.init_gamename(gameFunctionName)

        return binding.root
    }

    private fun rungame(description: String) {
        binding.description.visibility = View.VISIBLE
        binding.TextInputLayoutSpeed.visibility=View.VISIBLE
        binding.AutoCompleteTextViewSpeed.visibility=View.VISIBLE
        binding.TextInputLayoutColour.visibility = View.VISIBLE
        binding.AutoCompleteTextViewColour.visibility = View.VISIBLE
        binding.startButton.visibility = View.VISIBLE
        binding.gameOfLifeView.visibility = View.VISIBLE
        binding.refreshButton.visibility = View.VISIBLE

        binding.description.text = description

        val colors = listOf(
            "binary",
            "inferno",
            "viridis",
            "plasma",
            "magma",
            "cividis",
            "Greys",
            "Blues",
            "Reds",
            "hot",
            "cool"
        )
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, colors)

        binding.AutoCompleteTextViewColour.setAdapter(adapter)
        binding.AutoCompleteTextViewColour.setOnItemClickListener { parent, view, position, id ->
            val selectedColor = colors[position]
            binding.gameOfLifeView.setColorScheme(selectedColor)
        }

        val speed = listOf(
            "0.25",
            "0.50",
            "0.75",
            "1.0",
            "1.5",
            "2.0",
            "2.5",
            "3.0"
        )
        val adapter2 =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, speed)

        binding.AutoCompleteTextViewSpeed.setAdapter(adapter2)
        binding.AutoCompleteTextViewSpeed.setOnItemClickListener { parent, view, position, id ->
            val selectedSpeed= speed[position]
            binding.gameOfLifeView.setSpeed(selectedSpeed)
        }

        binding.startButton.setOnClickListener {
            if (isRunninggameoflife) {
                binding.gameOfLifeView.stopAnimation()
                binding.startButton.text = "Start"
            } else {
                binding.gameOfLifeView.startAnimation(gameFunctionName)
                binding.startButton.text = "Stop"
            }
            isRunninggameoflife = !isRunninggameoflife
        }

        binding.refreshButton.setOnClickListener {
            binding.gameOfLifeView.refresh()
        }


    }

    override fun onPause() {
        super.onPause()
        binding.gameOfLifeView.stopAnimation()
    }


}