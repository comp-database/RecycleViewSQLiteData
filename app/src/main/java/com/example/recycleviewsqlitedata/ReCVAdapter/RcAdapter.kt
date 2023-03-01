package com.example.recycleviewsqlitedata.ReCVAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewsqlitedata.Model.Contacts
import com.example.recycleviewsqlitedata.R

class RcAdapter(private val context: Context,
                private val dataset : ArrayList<Contacts>
): RecyclerView.Adapter<RcAdapter.ItemViewHolder>() {

    // code for onclicklistener
    lateinit var mListener : onItemClicklistener
    interface onItemClicklistener{
        fun onItemlick(position: Int)
    }
    fun setOnItemClickListener(listener : onItemClicklistener){
        mListener = listener
    }

    //extra one more parameter
    class ItemViewHolder(private val view: View ,listener: onItemClicklistener) : RecyclerView.ViewHolder(view){
        val nameView : TextView = view.findViewById(R.id.item_name)
        val meaningView : TextView = view.findViewById(R.id.item_meaning)
        val idView : TextView = view.findViewById(R.id.item_id)

        val button : Button = view.findViewById(R.id.Btn)
        init {
            // here instead of button you can set any intended view
            button.setOnClickListener {
                listener.onItemlick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)//inflate actual item view
        return ItemViewHolder(adapterLayout,mListener)//mListener for on click listener
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
//        holder.nameView.text = context.getString(item.name)
//        holder.meaningView.text= context.getString(item.meaning)
        holder.nameView.setText(item.name)
        holder.meaningView.setText(item.meaning)
        holder.idView.setText(item.id.toString())
    }
    override fun getItemCount(): Int {
        return dataset.size
    }

}