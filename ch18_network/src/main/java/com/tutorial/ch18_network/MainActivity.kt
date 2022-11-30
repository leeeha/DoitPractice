package com.tutorial.ch18_network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutorial.ch18_network.databinding.ActivityMainBinding
import com.tutorial.ch18_network.model.PageListModel
import com.tutorial.ch18_network.recycler.MyAdapter
import com.tutorial.ch18_network.retrofit.API_KEY
import com.tutorial.ch18_network.retrofit.BASE_URL
import com.tutorial.ch18_network.retrofit.NetworkService
import com.tutorial.ch18_network.retrofit.QUERY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkService = retrofit.create(NetworkService::class.java)
        val call: Call<PageListModel> = networkService.getNewsList(
            QUERY,
            API_KEY,
            1,
            10
        )

        call.enqueue(object: Callback<PageListModel> {
            override fun onResponse(
                call: Call<PageListModel>,
                response: Response<PageListModel>
            ) {
                if(response.isSuccessful) {
                    binding.retrofitRecyclerView.layoutManager =
                        LinearLayoutManager(this@MainActivity)
                    binding.retrofitRecyclerView.adapter =
                        MyAdapter(this@MainActivity, response.body()?.articles)
                    binding.retrofitRecyclerView.addItemDecoration(
                        DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
                    )
                }
            }

            override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                Log.e("RETROFIT RESULT", t.message.toString())
                call.cancel()
            }
        })
    }
}