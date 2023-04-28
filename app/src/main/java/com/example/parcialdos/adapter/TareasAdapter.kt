package com.example.parcialdos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialdos.models.Model
import com.example.parcialdos.R

class TareasAdapter(private val TareasList:ArrayList<Model>):
    RecyclerView.Adapter<TareasAdapter.ViewHolder>() {

    private lateinit var itemListener: onTareaListClick

    interface onTareaListClick {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(clickListener: onTareaListClick){
        itemListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_tareas_list, parent, false)
        return ViewHolder(itemView, itemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current = TareasList[position]
        holder.tvName.text = current.tsName

    }

    override fun getItemCount(): Int {
        return TareasList.size
    }


    class ViewHolder(itemView: View, clickListener: onTareaListClick):RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvTarea)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}