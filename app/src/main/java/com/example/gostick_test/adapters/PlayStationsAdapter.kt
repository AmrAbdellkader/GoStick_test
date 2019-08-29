package com.example.gostick_test.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gostick_test.R
import com.example.gostick_test.main2ActivityFragments.FragPlayStationsInfo
import com.example.gostick_test.models.PlayStationModel

class PlayStationsAdapter(private val activity: FragmentActivity, private var playStationList: ArrayList<PlayStationModel>)
    : RecyclerView.Adapter<PlayStationsAdapter.PlayStationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayStationHolder {
        return PlayStationHolder(LayoutInflater.from(parent.context).inflate(R.layout.lay_playstation_item, parent, false))
    }

    override fun getItemCount(): Int {
        return playStationList.size
    }

    override fun onBindViewHolder(holder: PlayStationHolder, position: Int) {
        val playStationItem = playStationList[position]
        holder.playStationName.text = playStationItem.name
        holder.freeRooms.text = playStationItem.freeRooms

        holder.playStationName.setOnClickListener {
            activity.getSharedPreferences("sd", Context.MODE_PRIVATE).edit().putString("PlayStationName", holder.playStationName.text.toString()).apply()
            activity.getSharedPreferences("sd", Context.MODE_PRIVATE).edit().putString("freeRooms", holder.freeRooms.text.toString()).apply()
            activity.getSharedPreferences("sd", Context.MODE_PRIVATE).edit().putString("email", playStationItem.email).apply()
            showFragPlayStationInfo()
        }
    }

    class PlayStationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playStationName = itemView.findViewById(R.id.btn_playstation_name)       as Button
        val freeRooms       = itemView.findViewById(R.id.txt_number_of_free_rooms)   as TextView
    }

    private fun showFragPlayStationInfo(){
        FragPlayStationsInfo().show(activity.supportFragmentManager, "pop")

    }
}