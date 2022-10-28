package com.example.gestaofinanceira.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaofinanceira.R
import com.example.gestaofinanceira.model.Task

class TaskAdapter(val tasks : List<Task>):RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var listener : OnUserItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {


        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_lucrendiv,parent,false)
        return TaskViewHolder(itemView, listener)


    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.TaskName.text = tasks[position].name
        holder.TaskValor.text = tasks[position].valor


    }

    override fun getItemCount(): Int {
        return tasks.size
    }



   fun setOnUserItemClickListener(listener: OnUserItemClickListener){
        this.listener = listener
   }

    class TaskViewHolder(itemView:View,val listener: OnUserItemClickListener?) : RecyclerView.ViewHolder(itemView) {
            val TaskName: TextView = itemView.findViewById(R.id.lucrediv_nome)
            val TaskValor: TextView = itemView.findViewById(R.id.lucrediv_valor)

        init{
            itemView.setOnClickListener{
                listener?.onClick(it,adapterPosition)

            }
            itemView.setOnLongClickListener{
                listener?.onLongClick(it,adapterPosition)
                    true
            }
        }
    }
}
