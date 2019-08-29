package com.example.gostick_test.main2ActivityFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.gostick_test.R
import kotlinx.android.synthetic.main.lay_frag_playstation_info.*

class FragPlayStationsInfo: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_frag_playstation_info, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.getSharedPreferences("fragment", Context.MODE_PRIVATE).edit().putString("fragment", "FragPlayStationInfo").apply()
        txt_playstation_name.text = activity!!.getSharedPreferences("sd", AppCompatActivity.MODE_PRIVATE).getString("PlayStationName", "").toString()
        txt_free_rooms.text = activity!!.getSharedPreferences("sd", AppCompatActivity.MODE_PRIVATE).getString("freeRooms", "").toString()
        btn_reserve.setOnClickListener {
            navigateToFragReservation()
            this.dismiss()
        }
    }
    private fun navigateToFragReservation(){
        activity!!.supportFragmentManager.beginTransaction().addToBackStack("hjjh").replace(R.id.content_main, FragReservation()).commit()

    }
}