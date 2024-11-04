package com.example.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.view.viewholder.GuestViewHolder

class GuestsAdapter: RecyclerView.Adapter<GuestViewHolder>() {

    private var guestList: List<GuestModel> = listOf()
    private lateinit var listener: OnGuestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context),parent,false) // o xml row_guest (linha)
        return GuestViewHolder(item,listener)
    } //componente criado

    override fun getItemCount(): Int {
        return guestList.count()
    }//dados contados

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) { // vai passar cada um da lista
        holder.bind(guestList[position]) // vai selecionar todos que o questList.count obter
    }//dados adcionados no ViewHolder

    fun updateGuests(list: List<GuestModel>) {
        guestList = list
        notifyDataSetChanged()
    }

    fun attachListener(guestListener: OnGuestListener) {
        listener = guestListener

    }
}