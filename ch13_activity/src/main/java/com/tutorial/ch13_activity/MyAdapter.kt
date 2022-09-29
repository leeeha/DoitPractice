package com.tutorial.ch13_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.ch13_activity.databinding.ItemRecyclerviewBinding

// 아이템 뷰를 저장하는 뷰 홀더 클래스
// item_recyclerview.xml 파일과의 뷰 바인딩
class MyViewHolder(val binding: ItemRecyclerviewBinding)
    : RecyclerView.ViewHolder(binding.root)

// 생성자에서 데이터 전달 받음.
class MyAdapter(val datas: MutableList<String>?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder
            = MyViewHolder(ItemRecyclerviewBinding
        .inflate(LayoutInflater.from(parent.context), parent, false))

    // position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas!![position]
    }

    // 전체 데이터 개수 리턴
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }
}
