package com.example.gostick_test.main2ActivityFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gostick_test.MainActivity
import com.example.gostick_test.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.lay_frag_after_reservation.*
import java.text.SimpleDateFormat
import java.util.*

class FragAfterReservation:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_frag_after_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.getSharedPreferences("fragment", Context.MODE_PRIVATE).edit().putString("fragment", "FragAfterReservation").apply()
        var timeOfEndReservation: String
        var timeOfStartReservation: String
        txt_after_reservation_time.text = activity!!.getSharedPreferences("reservation", Context.MODE_PRIVATE).getString("time", "").toString()
        txt_playstation_name.text = activity!!.getSharedPreferences("reservation", Context.MODE_PRIVATE).getString("PlayStationName", "").toString()
        txt_number_of_hours.text = activity!!.getSharedPreferences("reservation", Context.MODE_PRIVATE)
            .getString("numberOfHours", " ").toString()

        btn_start_reservation.setOnClickListener {
            btn_delete_reservation.isEnabled = false
            btn_end_reservation.isEnabled = true
            timeOfStartReservation = SimpleDateFormat("HH:mm:ss", Locale("en")).format(Calendar.getInstance().time).toString()
            txt_start_time.text = timeOfStartReservation
            startReservation()
            btn_start_reservation.isEnabled = false
        }
        btn_end_reservation.setOnClickListener {
            timeOfEndReservation = SimpleDateFormat("HH:mm:ss", Locale("en")).format(Calendar.getInstance().time).toString()
            txt_end_time.text = timeOfEndReservation
            endReservation()
        }
        btn_delete_reservation.setOnClickListener {
            deleteReservation()
        }
    }

    private fun startReservation(){
        val playStationName = activity!!.getSharedPreferences("reservation", Context.MODE_PRIVATE).getString("PlayStationName", "").toString()
        val playStationEmail =activity!!.getSharedPreferences("sd", Context.MODE_PRIVATE).getString("email", "").toString()
        FirebaseFirestore.getInstance().collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfStartFromUser",txt_start_time.text.toString().trim())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }

        FirebaseFirestore.getInstance().collection("activeReservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfStartFromUser",txt_start_time.text.toString().trim())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
        /*FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfStartReservation",timeOfStartReservation)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
        FirebaseFirestore.getInstance().collection("playStations")
            .document(playStationEmail)
            .collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfStartReservation",timeOfStartReservation)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }*/

    }

    private fun endReservation(){
        val playStationEmail =activity!!.getSharedPreferences("sd", Context.MODE_PRIVATE).getString("email", "").toString()
        val playStationName = activity!!.getSharedPreferences("reservation", Context.MODE_PRIVATE).getString("PlayStationName", "").toString()

        FirebaseFirestore.getInstance().collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfEndFromUser",txt_end_time.text.toString().trim())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }else{
                    FirebaseFirestore.getInstance().collection("activeReservations")
                        .document(playStationName + txt_after_reservation_time.text.toString().trim()).delete()
                        .addOnCompleteListener {
                            if (!it.isSuccessful){
                                Toast.makeText(activity!!, it.exception!!.message, Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }


        /*FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfEndReservation",txt_end_time.text.toString().trim())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }

        FirebaseFirestore.getInstance().collection("playStations")
            .document(playStationEmail)
            .collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfEndReservation",timeOfEndReservation)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }*/
    }

    private fun deleteReservation(){
        val playStationEmail =activity!!.getSharedPreferences("sd", Context.MODE_PRIVATE).getString("email", "").toString()
        val playStationName = activity!!.getSharedPreferences("reservation", Context.MODE_PRIVATE).getString("PlayStationName", "").toString()
        val timeOfCancelReservation = SimpleDateFormat("HH:mm:ss", Locale("en")).format(Calendar.getInstance().time).toString()
        FirebaseFirestore.getInstance().collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("timeOfCancelFromUser",timeOfCancelReservation)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }else{
                    FirebaseFirestore.getInstance().collection("activeReservations")
                        .document(playStationName + txt_after_reservation_time.text.toString().trim()).delete()
                        .addOnCompleteListener {
                            if (!it.isSuccessful){
                                Toast.makeText(activity!!, it.exception!!.message, Toast.LENGTH_LONG).show()
                            }else{
                                startActivity(Intent(activity!!, MainActivity::class.java))
                            }
                        }
                }
            }


        /*FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.email.toString())
            .collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("completed",false)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
        FirebaseFirestore.getInstance().collection("playStations")
            .document(playStationEmail)
            .collection("reservations")
            .document(playStationName + txt_after_reservation_time.text.toString().trim())
            .update("completed",false)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }*/
    }
}