package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cellularautomata.PlayActivity
import com.example.cellularautomata.databinding.StudyListAdapterBinding

class PlayListAdapter(var context:Context,var arrayList: ArrayList<String>):RecyclerView.Adapter<PlayListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:StudyListAdapterBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=StudyListAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView2.text=arrayList[position]
    }

}