package com.example.firebasestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class UserListActivity : AppCompatActivity() {
    private lateinit var userListRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_activity)
        userListRecyclerView = findViewById(R.id.recyclerView)
    }
}