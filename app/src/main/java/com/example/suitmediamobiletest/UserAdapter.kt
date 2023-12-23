package com.example.suitmediamobiletest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter(private val userList: List<User>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(user)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        private val userEmailTextView: TextView = itemView.findViewById(R.id.userEmailTextView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)

        fun bind(user: User) {
            userNameTextView.text = "${user.first_name} ${user.last_name}"
            userEmailTextView.text = user.email

            // Load and display the avatar image using Picasso
            Picasso.get().load(user.avatar).into(avatarImageView)
        }
    }
}
