package com.tutorial.ch17_database

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.tutorial.ch17_database.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_add_save -> {
                // 유저가 입력한 값을 데이터베이스에 저장하기
                val inputData = binding.addEditView.text.toString()
                val db = DBHelper(this).writableDatabase
                db.execSQL(
                    "insert into TODO_TB (todo) values (?)",
                    arrayOf(inputData)
                )
                db.close()

                // 인텐트의 부가 데이터에 유저의 입력값 저장하기
                val intent = this.intent
                intent.putExtra("result", inputData)
                setResult(Activity.RESULT_OK, intent)
                finish()
                true
            }
            else -> true
        }
}