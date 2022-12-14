package com.tutorial.ch13_activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutorial.ch13_activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, 10)
        }

        // 번들 객체에 저장된 데이터를 가져와서 데이터 리스트 초기화
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: mutableListOf()

        // 레이아웃 매니저 지정
        val layoutManager = LinearLayoutManager(this) // 기본적으로 VERTICAL
        binding.mainRecyclerView.layoutManager = layoutManager

        // 어댑터 지정
        adapter = MyAdapter(datas)
        binding.mainRecyclerView.adapter = adapter

        // 아이템 구분선 추가
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10 && resultCode == Activity.RESULT_OK){
            // 다른 액티비티로부터 전달 받은 부가 데이터를 꺼내서 리스트에 추가
            data!!.getStringExtra("result")?.let {
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    // 번들 객체에 데이터 리스트 담기
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", datas?.let { ArrayList(it) })
    }
}
