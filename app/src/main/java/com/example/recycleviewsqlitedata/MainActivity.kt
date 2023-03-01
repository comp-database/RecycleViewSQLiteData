package com.example.recycleviewsqlitedata

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import com.example.recycleviewsqlitedata.DataBase.MydbHelper
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.KEY_ROW_ID
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.COLUMN_MEANING
import com.example.recycleviewsqlitedata.DataBase.MydbHelper.FeedEntry.COLUMN_NAME
import com.example.recycleviewsqlitedata.Model.Contacts
import com.example.recycleviewsqlitedata.ReCVAdapter.RcAdapter
import com.example.recycleviewsqlitedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var db : SQLiteDatabase
//    lateinit var rs : Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myDataset = getItemsList()
        val recyclerView = binding.recyclerView
        var adapterRc = RcAdapter(this,myDataset)
        recyclerView.adapter = adapterRc


        // code for click listener
        adapterRc.setOnItemClickListener(object : RcAdapter.onItemClicklistener{
            override fun onItemlick(position: Int) {
                val data = myDataset[position]
                val intent = Intent(this@MainActivity , detailActivity::class.java)
                intent.putExtra("name",data.name)
                intent.putExtra("meaning",data.meaning)
                intent.putExtra("id",data.id.toString())
                startActivity(intent)// start the activity
            }
        })
    }

    private fun getItemsList(): ArrayList<Contacts> {
        //creating the instance of DatabaseHandler class
        val databaseHandler: MydbHelper = MydbHelper(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val datalist: ArrayList<Contacts> = databaseHandler.allDataList()
        return datalist
    }
}
