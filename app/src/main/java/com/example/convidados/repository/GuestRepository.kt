package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
import java.sql.SQLDataException

class GuestRepository(context: Context) {


    private val guestDataBase = GuestDataBase.getDataBase(context).guestDAO()

    fun insert(guest: GuestModel): Boolean {
        return guestDataBase.insert(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return guestDataBase.update(guest) > 0
    }

    fun delete(id: Int) {
        val getId = get(id)
        guestDataBase.delete(getId)
    }

    fun get(id: Int): GuestModel {
        return guestDataBase.get(id)
    }

    fun getAll(): List<GuestModel> {
        return guestDataBase.getAll()
    }

    fun getPresence(): List<GuestModel> {
        return guestDataBase.getPresence()
    }

    fun getAbsent(): List<GuestModel> {
        return guestDataBase.getAbsent()
    }
}