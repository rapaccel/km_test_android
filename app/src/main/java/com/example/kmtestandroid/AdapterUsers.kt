package com.example.kmtestandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class AdapterUsers(val context: Context, var dataList: List<DataUsers>): RecyclerView.Adapter<AdapterUsers.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUsers.MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_users, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterUsers.MyViewHolder, position: Int) {
        val item= dataList[position]
        holder.name.text = item.name
        holder.email.text = item.email
        Glide.with(context)
            .load(item.image)
            .into(holder.iamge)
        holder.relative.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("selectedName", item.name)
            editor.apply()
            if (context is Activity) {
                context.finish()
            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    inner class MyViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val relative: RelativeLayout = item.findViewById<RelativeLayout>(R.id.relative)
        val name : TextView = item.findViewById(R.id.userName)
        val email : TextView = item.findViewById(R.id.userEmail)
        val iamge : ImageView = item.findViewById(R.id.userImage)
    }

}