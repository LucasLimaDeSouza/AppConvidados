package com.example.convidados.constants

class DataBaseConstants private constructor(){

    object GUESt {
        const val ID = "guestid"
        const val NAME_TABLE = "guest"

        object COLUMNS {
            const val ID = "id"
            const val PRESENCE = "presence"
            const val NAME = "name"
        }
    }
}