package com.example.cellularautomata

import adapter.GameoflifeAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cellularautomata.databinding.ActivityGameoflifeBinding

class GameoflifeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameoflifeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameoflifeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        var array: ArrayList<String> = arrayListOf(
            "1. Game of Life",
            "2. Majority rule xor vonneumann",
            "3. Majority rule xor moore_rule",
            "4. SumMod2 moore  rule",
            "5. SumMod2 vonneumann",
            "more coming",
            "more coming",
            "more coming",
            "more coming",
            "more coming",
            "more coming",
            "more coming",
            "more coming",
            "more coming",
            "more coming"
            )
        val adapter = GameoflifeAdapter(this,array)
        binding.recylerView.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.recylerView.adapter=adapter
    }
}