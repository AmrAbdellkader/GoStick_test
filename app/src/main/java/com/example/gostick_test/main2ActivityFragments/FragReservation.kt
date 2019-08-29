package com.example.gostick_test.main2ActivityFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gostick_test.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.lay_reservation.*
import java.text.SimpleDateFormat
import java.util.*

class FragReservation: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_reservation, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var number = 0

        txt_playstation_name.text = activity!!.getSharedPreferences("sd", Context.MODE_PRIVATE)
            .getString("PlayStationName", "").toString()

        txt_free.text = activity!!.getSharedPreferences("sd", Context.MODE_PRIVATE)
            .getString("freeRooms", "0").toString()


        btn_hours_up.setOnClickListener {
            number +=1
            txt_number_of_hours.text = number.toString()
        }

        btn_hours_down.setOnClickListener {
            number -= 1
            txt_number_of_hours.text = number.toString()
        }

        btn_open.setOnClickListener {
            txt_number_of_hours.text = getString(R.string.open)
        }

        btn_confirm.setOnClickListener {
            progressBar4.visibility = View.VISIBLE
            makeReservation()

        }
    }

    private fun navigateToFragAfterReservation(){
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack("afterReservation")
            .replace(R.id.content_main, FragAfterReservation())
            .commit()

    }

    private fun makeReservation(){
        val playStationName =activity!!.getSharedPreferences("sd", Context.MODE_PRIVATE).getString("PlayStationName", "").toString()
        val playStationEmail =activity!!.getSharedPreferences("sd", Context.MODE_PRIVATE).getString("email", "").toString()
        val time = SimpleDateFormat("HH:mm:ss", Locale("en")).format(Calendar.getInstance().time).toString()

        val editSharedPreferences = activity!!.getSharedPreferences("reservation", Context.MODE_PRIVATE).edit()
        editSharedPreferences.putString("PlayStationName", txt_playstation_name.text.toString())
        editSharedPreferences.putString("numberOfHours", txt_number_of_hours.text.toString())
        editSharedPreferences.putString("time", time)
        editSharedPreferences.apply()
        val reservation: Map<String, Any> = hashMapOf(
            "numberOfHours" to txt_number_of_hours.text.toString(),
            "playStationName" to txt_playstation_name.text.toString(),
            "userEmail" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "timeOfReserve" to time,
            "timeOfAcceptedFromOwner" to "Didn't accepted from owner",
            "timeOfCancelFromUser" to "Didn't canceled from user",
            "timeOfCancelFromOwner" to "Didn't canceled from owner",
            "timeOfStartFromUser" to "Didn't start from user",
            "timeOfEndFromUser" to "Didn't end from user",
            "timeOfStartFromOwner" to "Didn't start from owner",
            "timeOfEndFromOwner" to "Didn't end from owner",
            "playStationEmail" to playStationEmail
        )



        FirebaseFirestore.getInstance().collection("activeReservations")
            .document(playStationName + time).set(reservation).addOnCompleteListener {
                if (!it.isSuccessful){
                    Toast.makeText(activity!!, it.exception!!.message, Toast.LENGTH_LONG).show()
                }else{
                    FirebaseFirestore.getInstance().collection("reservations")
                        .document(playStationName + time).set(reservation).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                progressBar4.visibility = View.GONE
                                navigateToFragAfterReservation()
                            }else{
                                progressBar4.visibility = View.GONE
                                Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }

/*FirebaseFirestore.getInstance().collection("users")
    .document(FirebaseAuth.getInstance().currentUser?.email.toString())
    .collection("reservations")
    .document(playStationName + time)
    .set(reservation).addOnCompleteListener {
        if (it.isSuccessful){
            progressBar4.visibility = View.GONE
            navigateToFragAfterReservation()
        }else{
            progressBar4.visibility = View.GONE
            Toast.makeText(activity!!, it.exception!!.message, Toast.LENGTH_LONG).show()
        }
    }
        FirebaseFirestore.getInstance().collection("playStations")
            .document(playStationEmail)
            .collection("reservations")
            .document(playStationName + time)
            .set(reservation).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Toast.makeText(activity!!, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }*/
    }
}