package com.example.gostick_test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gostick_test.R
import com.example.gostick_test.models.ReservationsModel

class ReservationsAdapter(private var reservationsList: ArrayList<ReservationsModel>): RecyclerView.Adapter<ReservationsAdapter.ReservationsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationsHolder {
        return ReservationsHolder(LayoutInflater.from(parent.context).inflate(R.layout.lay_reservations_item, parent, false))
    }

    override fun getItemCount(): Int {
        return reservationsList.size
    }

    override fun onBindViewHolder(holder: ReservationsHolder, position: Int) {
        val reservationItem = reservationsList[position]
        holder.PlayStationName.text = reservationItem.playStationName
        holder.time.text = reservationItem.timeOfReserve
        holder.numberOfHours.text = reservationItem.numberOfHours
    }

    class ReservationsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time = itemView.findViewById(R.id.txt_reservation_time)       as TextView
        val PlayStationName = itemView.findViewById(R.id.txt_reservation_playstation_name)   as TextView
        val numberOfHours = itemView.findViewById(R.id.txt_reservation_number_of_hours)   as TextView

    }}