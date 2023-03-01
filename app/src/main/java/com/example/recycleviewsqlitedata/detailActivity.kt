package com.example.recycleviewsqlitedata

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import com.example.recycleviewsqlitedata.DataBase.MydbHelper
import com.example.recycleviewsqlitedata.databinding.ActivityDetailBinding

class detailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var receiver : String
    lateinit var db : SQLiteDatabase
    lateinit var curser_new : Cursor
    lateinit var adapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data_name = intent.extras?.getString("name")
        val data_meaning = intent.extras?.getString("meaning")
        val data_id = intent.extras?.getString("id").toString()

        binding.textView3.text = data_name
        binding.textView2.text = data_meaning
        binding.textView.text = data_id

        val list = getItemsList()
        val adapter = ArrayAdapter<String>(this@detailActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@detailActivity, "you Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_SHORT).show()
                receiver = adapterView?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        var helper = MydbHelper(this@detailActivity)
        db = helper.readableDatabase
        curser_new = db.rawQuery("SELECT * FROM TRANSACT",null)
        binding.button.setOnClickListener {
            //insert Record
            var cv_new = ContentValues()
            cv_new.put("SENDER", binding.textView2.text.toString())
            cv_new.put("RECEIVER",receiver.toString())
            cv_new.put("AMOUNT",binding.amount.text.toString())
            db.insert("TRANSACT", null, cv_new)
            curser_new.requery()
            Toast.makeText(this, "transaction complete with $receiver with amount ${binding.amount.text.toString()}", Toast.LENGTH_SHORT).show()
        }
        binding.listView.setOnClickListener {
            val intent = Intent(this,ListViewOfData::class.java)
            startActivity(intent)
        }
    }

    private fun getItemsList(): ArrayList<String> {
        //creating the instance of DatabaseHandler class
        val databaseHandler: MydbHelper = MydbHelper(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val datalist: ArrayList<String> = databaseHandler.nameDataList()
        return datalist
    }
}