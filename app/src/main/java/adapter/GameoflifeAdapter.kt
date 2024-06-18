package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cellularautomata.databinding.StudyListAdapterBinding

class GameoflifeAdapter(var content: Context, var arrayList: ArrayList<String>): RecyclerView.Adapter<GameoflifeAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: StudyListAdapterBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=StudyListAdapterBinding.inflate(LayoutInflater.from(content),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView2.text=arrayList[position]
    }

}