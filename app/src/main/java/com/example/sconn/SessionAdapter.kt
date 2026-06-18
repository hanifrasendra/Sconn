package com.example.sconn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sconn.data_class.Session
import com.example.sconn.databinding.ItemSessionBinding

class SessionAdapter(private var sessions: List<Session>) :
    RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(val binding: ItemSessionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ItemSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        holder.binding.apply {
            tvItemTitle.text = session.title
            tvItemSubject.text = session.subject
            tvItemDate.text = session.date
            tvItemTime.text = session.time
            tvItemLocation.text = session.placeName
        }
    }

    override fun getItemCount(): Int = sessions.size

    fun updateData(newSessions: List<Session>) {
        sessions = newSessions
        notifyDataSetChanged()
    }
}