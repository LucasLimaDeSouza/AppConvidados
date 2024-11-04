package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.convidados.constants.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + DataBaseConstants.GUESt.NAME_TABLE + " ( "
                    + DataBaseConstants.GUESt.COLUMNS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DataBaseConstants.GUESt.COLUMNS.NAME + " TEXT, "
                    + DataBaseConstants.GUESt.COLUMNS.PRESENCE + " INTEGER );"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}


