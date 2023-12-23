package com.example.suitmediamobiletest


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmediamobiletest.FirstActivity.Companion.avatar
import com.example.suitmediamobiletest.FirstActivity.Companion.selectedName
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken


class ThirdActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: UserAdapter
    private var users: MutableList<User> = mutableListOf()
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        // Set up RecyclerView
        adapter = UserAdapter(users, this)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val backButton = findViewById<ImageView>(R.id.thirdBackButton)
        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            // Refresh data when swiping
            currentPage = 1
            fetchData()
        }

        // Load more data when scrolling to the bottom
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    // Load more data when reaching the end
                    currentPage++
                    fetchData()
                }
            }
        })

        // Initial data fetch
        fetchData()

        backButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchData() {
        val url = "https://reqres.in/api/users?page=$currentPage&per_page=10"
        FetchDataAsyncTask(object : FetchDataAsyncTask.OnDataFetchedListener {
            override fun onDataFetched(result: String) {
                runOnUiThread {
                    // UI updates must be done on the main thread
                    val userList = parseJson(result)
                    if (currentPage == 1) {
                        users.clear()
                    }
                    users.addAll(userList)
                    adapter.notifyDataSetChanged()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }).execute(url)
    }

    private fun parseJson(json: String): List<User> {
        val gson = Gson()
        val jsonObject = gson.fromJson(json, JsonObject::class.java)

        // Assuming the key for the user list is "data"
        val dataArray = jsonObject.getAsJsonArray("data")
        return gson.fromJson(dataArray, object : TypeToken<List<User>>() {}.type)
    }

    override fun onItemClick(user: User) {
        // Navigate to SecondActivity and pass the username
        selectedName = "${user.first_name} ${user.last_name}"
        avatar = "${user.avatar}"
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

}

