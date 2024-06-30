package adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.MaterialListAdapter2Binding

class MaterialListAdapter(var content:Context, var arrayList:ArrayList<String>, var dataList:ArrayList<String>):RecyclerView.Adapter<MaterialListAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:MaterialListAdapter2Binding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=MaterialListAdapter2Binding.inflate(LayoutInflater.from(content),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.text.text=arrayList[position]
        holder.binding.text.setOnClickListener {
            val bundle = Bundle().apply {
                Log.e("dashboard", "position : $position")
                putString("title", arrayList[position])
                putString("data", dataList[position])
                putInt("material_number", position)
            }
            holder.itemView.findNavController().navigate(R.id.action_materialFragment_to_materialDisplayFragment,bundle)
        }

    }


}


