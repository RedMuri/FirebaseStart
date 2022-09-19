package com.example.firebasestart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainAdapter(private var users: ArrayList<Pair<String, User?>>) :
    Adapter<MainAdapter.UsersViewHolder>() {
    class UsersViewHolder(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textViewName)
        val lastName: TextView = itemView.findViewById(R.id.textViewLastName)
        val type: TextView = itemView.findViewById(R.id.textViewType)
        val age: TextView = itemView.findViewById(R.id.textViewAge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val user = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UsersViewHolder(user)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = user.second?.name
        holder.lastName.text = user.second?.lastName
        holder.type.text = user.second?.type
        holder.age.text = user.second?.age
    }

    override fun getItemCount() = users.size

    fun setUsers(new: ArrayList<Pair<String,User?>>) {
        this.users = new
        notifyDataSetChanged()
    }

    fun getUsers() = this.users
}