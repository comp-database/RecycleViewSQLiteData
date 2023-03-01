package com.example.recycleviewsqlitedata.DataBase

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.Contacts.SettingsColumns.KEY
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.COLUMN_MEANING
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.COLUMN_NAME
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.KEY_ROW_ID
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.COLUMN_SENDER
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.COLUMN_RECEIVER
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.COLUMN_AMOUNT
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.KEY_TRANS_ID

import com.example.recycleviewsqlitedata.Model.Contacts
import com.example.recycleviewsqlitedata.Model.transactions

class MydbHelper(context: Context) : SQLiteOpenHelper(context,"DATA",null,2) {

    override fun onCreate(db: SQLiteDatabase?) {
        // primary key autoincrement increments the id key
        db?.execSQL("CREATE TABLE ACTABLE ($KEY_ROW_ID integer primary key autoincrement , $COLUMN_NAME TEXT , $COLUMN_MEANING TEXT)")
        db?.execSQL("CREATE TABLE TRANSACT($KEY_TRANS_ID integer primary key autoincrement , $COLUMN_SENDER TEXT , $COLUMN_RECEIVER TEXT , $COLUMN_AMOUNT INTEGER) ")

        // id Name Meaning are columns in db
        db?.execSQL("INSERT INTO ACTABLE (NAME,MEANING)VALUES('ONE','FIRST')")
        db?.execSQL("INSERT INTO ACTABLE (NAME,MEANING)VALUES('TWO','SECOND')")
        db?.execSQL("INSERT INTO ACTABLE (NAME,MEANING)VALUES('THREE','THIRD')")
        db?.execSQL("INSERT INTO ACTABLE (NAME,MEANING)VALUES('FOUR','FOURTH')")
        db?.execSQL("INSERT INTO ACTABLE (NAME,MEANING)VALUES('FIVE','FIFTH')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS TRANSACT ")
        db?.execSQL("DROP TABLE IF EXISTS ACTABLE ")
        onCreate(db)
    }

     object FeedEntry : BaseColumns{
        const val COLUMN_MEANING = "MEANING"
        const val KEY_ROW_ID = "_id"
        const val COLUMN_NAME= "NAME"
         const val KEY_TRANS_ID = "ID"
         const val COLUMN_SENDER = "SENDER"
         const val COLUMN_RECEIVER = "RECEIVER"
         const val COLUMN_AMOUNT = "AMOUNT"
    }
    //Method to read the records from database in form of ArrayList
    fun allDataList(): ArrayList<Contacts> {

        val datalist : ArrayList<Contacts> = ArrayList<Contacts>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM ACTABLE"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var meaning: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ROW_ID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                meaning = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEANING))

                val emp = Contacts(id = id, name = name, meaning = meaning)
                datalist.add(emp)

            } while (cursor.moveToNext())
        }
        return datalist
    }

    fun nameDataList(): ArrayList<String> {

        val datalist: ArrayList<String> = ArrayList<String>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM ACTABLE"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var meaning: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ROW_ID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                meaning = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEANING))

                val emp = Contacts(id = id, name = name, meaning = meaning).name.toString()
                datalist.add(emp)

            } while (cursor.moveToNext())
        }
        return datalist
    }

    fun getTransData() :ArrayList<transactions>{
        var arrayList :ArrayList<transactions> = ArrayList<transactions>()
        val selectQuery = "SELECT  * FROM TRANSACT"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id :Int
        var sender: String
        var receiver: String
        var amount: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TRANS_ID))
                sender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENDER))
                receiver = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECEIVER))
                amount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))

                val trans = transactions(id = id, sender = sender, receiver = receiver, amount = amount )
                arrayList.add(trans)

            } while (cursor.moveToNext())
        }
        return arrayList

    }

}