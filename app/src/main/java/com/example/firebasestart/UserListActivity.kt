package com.example.firebasestart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.TreeMap

class UserListActivity : AppCompatActivity() {
    private lateinit var userListRecyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter

    private lateinit var addButton: FloatingActionButton
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_activity)
        userListRecyclerView = findViewById(R.id.recyclerView)
        addButton =
            findViewById<FloatingActionButton?>(R.id.floatingActionButton).apply { setOnClickListener { addUser() } }
        mainAdapter = MainAdapter(arrayListOf())
        userListRecyclerView.adapter = mainAdapter
        userListRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val item = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val user = mainAdapter.getUsers()[viewHolder.adapterPosition]
                db.collection("Users").document(user.first).delete()
                Log.i("muri", user.first)
            }
        })
        item.attachToRecyclerView(userListRecyclerView)
        db = FirebaseFirestore.getInstance()

        db.collection("Users").addSnapshotListener { value, error ->
            if (value!=null) {
                val users = value.map { Pair(it.id, it.toObject<User>()) }
                mainAdapter.setUsers(ArrayList(users))
            } else{
                Log.i("muri","else")
            }
            if (error!=null){
                Log.i("muri","error: " + error.message.toString())
            }
        }
    }

    private fun addUser() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}