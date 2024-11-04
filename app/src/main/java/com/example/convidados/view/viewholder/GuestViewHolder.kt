package com.example.convidados.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

class GuestViewHolder(
    private val bind: RowGuestBinding, // recebe o xml row_guest
    private val listener: OnGuestListener
) : RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name

        bind.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Apagar COnvidado")
                .setMessage("Deseja mesmo apagar o convidado?")
                .setPositiveButton("sim") { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("não",null)
                .create()
                .show()
            true
        }
    }
}