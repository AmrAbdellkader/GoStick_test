package com.example.gostick_test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.gostick_test.R
import com.example.gostick_test.adapters.ReservationsAdapter
import com.example.gostick_test.models.ReservationsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.lay_frag_my_reservations.*

class FragMyReservations : DialogFragment() {

    private val fireStore by lazy{ }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_frag_my_reservations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

    }
    private fun setUpRecyclerView(){
        val reservationsModel = ArrayList<ReservationsModel>()
        val adapter = ReservationsAdapter( reservationsModel)
        recycler_view_reserve.adapter = adapter

        reservationsModel.clear()
        FirebaseFirestore.getInstance().collection("reservations").whereEqualTo(
            "userEmail", FirebaseAuth.getInstance().currentUser?.email.toString()).get().addOnSuccessListener {
            for (doc in it) {
                reservationsModel.add(doc.toObject(ReservationsModel::class.java))
                adapter.notifyDataSetChanged()
            }
        }
            .addOnFailureListener {
                Toast.makeText(activity!!, it.message, Toast.LENGTH_LONG).show()

            }
    }
}