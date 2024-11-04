package com.example.convidados.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.convidados.model.GuestModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


//class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {
@Database(entities = [GuestModel::class], version = 1)
abstract class GuestDataBase : RoomDatabase() {

    abstract fun guestDAO(): GuestDAO //

    //Singleton =  Controla o numero de instacia de uma class
    companion object {
        lateinit var INSTANCIE: GuestDataBase

        @OptIn(InternalCoroutinesApi::class)
        fun getDataBase(context: Context): GuestDataBase {
            if (!::INSTANCIE.isInitialized) {
                synchronized(GuestDataBase::class) {
                    INSTANCIE = Room.databaseBuilder(context, GuestDataBase::class.java, "guestdb")
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCIE
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DELETE FROM Guest")
            }

        }

    }

}