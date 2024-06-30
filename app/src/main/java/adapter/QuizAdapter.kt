package adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cellularautomata.R
import com.example.cellularautomata.databinding.MaterialListAdapterBinding


class QuizAdapter(var content: Context, var arrayList: ArrayList<String>): RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MaterialListAdapterBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding= MaterialListAdapterBinding.inflate(LayoutInflater.from(content),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView2.text=arrayList[position]
        val  bundle= Bundle()
        bundle.putInt("quiz_number",position)
        holder.binding.imgBox.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_quizFragment_to_quizQuestionFragment,bundle)
        }
    }
}