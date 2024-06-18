package com.example.cellularautomata

import adapter.PlayListAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cellularautomata.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Play"

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        //Uniform CA(no random initial state)(random position)(final_code2) --> with position matrix in final_code_3
        val arrayList:ArrayList<String> = arrayListOf(
            "1. Uniform CA(no random initial state)\n(final_code)",
            "2. Uniform CA(random initial state)\n(final_code)",
            "3. Uniform CA(no random initial state)\n(random position)\n(final_code2)",
            "4. Uniform CA(random initial state)\n(random position)\n(final_code2)",
            "5. Non Uniform CA(random initial state)\n(no random position)\n(final_code4)",
            "6. Non Uniform CA(random initial state)\n(random position)\n(final_code4)",
            "more coming",
            "more coming",
            "more coming",
            "more coming",
            "more coming"
        )
        val adapter=PlayListAdapter(this,arrayList)
        binding.recylerView.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.recylerView.adapter=adapter


    }
}