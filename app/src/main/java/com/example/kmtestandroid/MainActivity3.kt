package com.example.kmtestandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.kmtestandroid.databinding.ActivityMain3Binding
import org.json.JSONException
import org.json.JSONObject

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private var allUsers: MutableList<DataUsers> = mutableListOf()
    private lateinit var adapter: AdapterUsers
    private var currentPage = 1
    private val perPage = 10
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSwipeRefresh()
        getUsers(isRefresh = true)

        binding.backThirdScreen.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMenunggu.layoutManager = layoutManager
        adapter = AdapterUsers(this, allUsers)
        binding.recyclerViewMenunggu.adapter = adapter

        binding.recyclerViewMenunggu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == allUsers.size - 1) {
                    currentPage++
                    getUsers(isRefresh = false)
                }
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            currentPage = 1
            getUsers(isRefresh = true)
        }
    }

    private fun getUsers(isRefresh: Boolean) {
        isLoading = true
        if (isRefresh) binding.swipeRefresh.isRefreshing = true

        AndroidNetworking.get("https://reqres.in/api/users")
            .addQueryParameter("page", currentPage.toString())
            .addQueryParameter("per_page", perPage.toString())
            .addHeaders("Content-Type", "application/json")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        Log.d("Success", response.toString())
                        val getDataArray = response.getJSONArray("data")
                        val dataList = mutableListOf<DataUsers>()

                        for (i in 0 until getDataArray.length()) {
                            val item = getDataArray.getJSONObject(i)
                            dataList.add(
                                DataUsers(
                                    item.getString("first_name") + " " + item.getString("last_name"),
                                    item.getString("email"),
                                    item.getString("avatar"),
                                )
                            )
                        }

                        if (isRefresh) {
                            allUsers.clear()
                        }

                        allUsers.addAll(dataList)
                        adapter.notifyDataSetChanged()

                        // Tampilkan pesan jika data kosong
                        binding.tvEmptyState.visibility = if (allUsers.isEmpty()) View.VISIBLE else View.GONE
                    } catch (e: JSONException) {
                        Log.e("Error", e.message.toString())
                    } finally {
                        isLoading = false
                        binding.swipeRefresh.isRefreshing = false
                    }
                }

                override fun onError(error: ANError) {
                    Log.e("Error", error.message.toString())
                    isLoading = false
                    binding.swipeRefresh.isRefreshing = false
                }
            })
    }
}