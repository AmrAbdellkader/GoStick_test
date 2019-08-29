package com.example.gostick_test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gostick_test.R
import com.example.gostick_test.models.PlayStationModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PlayStationsFireBaseAdapter(/*val activity: FragmentActivity,*/options: FirestoreRecyclerOptions<PlayStationModel>) : FirestoreRecyclerAdapter<PlayStationModel, PlayStationsFireBaseAdapter.PlayStationViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayStationViewHolder {
      return PlayStationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lay_playstation_item, parent, false))   }

    override fun onBindViewHolder(p0: PlayStationViewHolder, p1: Int, p2: PlayStationModel) {
        p0.playStationName.text = p2.name
        p0.freeRooms.text = p2.freeRooms.toString()
        p0.playStationName.setOnClickListener {
          //  showFragPlayStationInfo()
        }
    }

    class PlayStationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val playStationName = itemView.findViewById(R.id.btn_playstation_name)       as Button
        val freeRooms       = itemView.findViewById(R.id.txt_number_of_free_rooms)   as TextView
    }

    /*private fun showFragPlayStationInfo(){
        com.example.gostick_test.main2ActivityFragments.FragPlayStationsInfo().show(activity.supportFragmentManager, "pop")

    }*/
}