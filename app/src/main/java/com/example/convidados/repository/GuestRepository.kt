package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
import java.sql.SQLDataException

class GuestRepository private constructor(context: Context) {


    private val guestDataBase = GuestDataBase(context)

    //Singleton =  Controla o numero de instacia de uma class
    companion object {
        private lateinit var repository: GuestRepository // variavel que representa/instancia a sua propria class

        fun getInstancie(context: Context): GuestRepository { // retorna a propria instacia
            if (!::repository.isInitialized) { // se não foi inicializado...
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val presence = if (guest.presence) 1 else 0
            val db = guestDataBase.writableDatabase
            val values = ContentValues()
            values.put(DataBaseConstants.GUESt.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUESt.COLUMNS.PRESENCE, presence)
            db.insert(DataBaseConstants.GUESt.NAME_TABLE, null, values)

            true

        } catch (e: Exception) {

            false

        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val presence = if (guest.presence) 1 else 0
            val db = guestDataBase.writableDatabase
            val values = ContentValues()
            values.put(DataBaseConstants.GUESt.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUESt.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUESt.COLUMNS.ID + " = ?" // selection(id) = args(?)
            val args = arrayOf(guest.id.toString())
            db.update(DataBaseConstants.GUESt.NAME_TABLE, values, selection, args)

            true

        } catch (e: Exception) {

            false

        }


    }

    fun delete(id: Int): Boolean {
        return try {

            val db = guestDataBase.writableDatabase
            val selection = DataBaseConstants.GUESt.COLUMNS.ID + " = ?" // selection(id) = args(?)
            val args = arrayOf(id.toString())
            db.delete(DataBaseConstants.GUESt.NAME_TABLE, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null

        return try {

            val db = guestDataBase.readableDatabase
            val listColumns = arrayOf(
                DataBaseConstants.GUESt.COLUMNS.NAME,
                DataBaseConstants.GUESt.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUESt.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            val cursor = db.query( // Cursor: O que aponta ou conta a quantidade de linha da tabela
                DataBaseConstants.GUESt.NAME_TABLE,
                listColumns,
                selection, args, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.NAME))
                val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.PRESENCE)) == 1) //retorna true ou false

                guest = GuestModel(id, name, presence) // presence da data class só aceita boolean

                cursor.close()
            }

            guest
        } catch (e: Exception) {
            guest // caindo aqui retorna null
        }

        return guest
    }

    fun getAll(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {

            val db = guestDataBase.readableDatabase
            val listColumns = arrayOf(
                DataBaseConstants.GUESt.COLUMNS.ID,
                DataBaseConstants.GUESt.COLUMNS.NAME,
                DataBaseConstants.GUESt.COLUMNS.PRESENCE
            )

            val cursor = db.query( // Cursor: O que aponta ou conta a quantidade de linha da tabela
                DataBaseConstants.GUESt.NAME_TABLE,
                listColumns,
                null, null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.PRESENCE))
                    list.add(
                        GuestModel(
                            id,
                            name,
                            presence == 1
                        )
                    ) // presence da data class só aceita boolean
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getPresence(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        return try {
            val db = guestDataBase.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.PRESENCE))
                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
            list
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getAbsent(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        return try {
            val db = guestDataBase.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUESt.COLUMNS.PRESENCE))
                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
            list
        } catch (e: Exception) {
            return list
        }
        return list
    }
}