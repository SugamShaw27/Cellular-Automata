package adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cellularautomata.ShowActivity
import com.example.cellularautomata.databinding.StudyListAdapterBinding

class StudyListAdapter(var content:Context,var arrayList:ArrayList<String>,var dataList:ArrayList<String>):RecyclerView.Adapter<StudyListAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:StudyListAdapterBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=StudyListAdapterBinding.inflate(LayoutInflater.from(content),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView2.text=arrayList[position]
        holder.binding.imgBox.setOnClickListener {
            val intent=Intent(content,ShowActivity::class.java)
            intent.putExtra("title",arrayList[position])
            intent.putExtra("data",dataList[position])
            content.startActivity(intent)
        }
    }


}


