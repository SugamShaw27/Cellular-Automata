package adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.MaterialListAdapter2Binding

class ExperimentListAdapter(var context:Context, var arrayList: ArrayList<String>):RecyclerView.Adapter<ExperimentListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MaterialListAdapter2Binding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=MaterialListAdapter2Binding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.text.text=arrayList[position]
        holder.binding.text.setOnClickListener {

            val bundle= Bundle()

            bundle.putInt("experiment_number",position)
            bundle.putString("experiment_name",arrayList[position])
            holder.itemView.findNavController().navigate(R.id.action_experimentFragment_to_experimentDisplayFragment,bundle)
        }
    }

}