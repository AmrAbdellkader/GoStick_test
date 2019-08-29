package com.example.gostick_test.main2ActivityFragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gostick_test.R
import com.example.gostick_test.adapters.PlayStationsAdapter
import com.example.gostick_test.models.PlayStationModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.lay_frag_playstations_list.*

class FragPlayStationsList:Fragment(), TextWatcher {

    override fun afterTextChanged(p0: Editable?) {
        filter(p0.toString())
    }

    @SuppressLint("DefaultLocale")
    private fun filter(s: String){
        val filteredList = ArrayList<PlayStationModel>()
        val playStationModel = ArrayList<PlayStationModel>()

        for (item in playStationModel){
            if (item.name.toLowerCase().contains(s.toLowerCase())){
                filteredList.add(item)
            }
        }
        val adapter = PlayStationsAdapter(activity!!, filteredList)
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_frag_playstations_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.getSharedPreferences("fragment", Context.MODE_PRIVATE).edit().putString("fragment", "FragPlayStationsList").apply()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkConnection()
        }
        recycler_view.layoutManager = LinearLayoutManager(activity!!)
        setUpRecyclerView()
        swipe_refresh_layout.setOnRefreshListener {
            activity!!.recreate()
        }

    }

    private fun setUpRecyclerView(){
        val playStationModel = ArrayList<PlayStationModel>()

        val adapter = PlayStationsAdapter(activity!!, playStationModel)
        recycler_view.adapter = adapter

        playStationModel.clear()
        FirebaseFirestore.getInstance().collection("playStations")
            .addSnapshotListener { snapshot, exception ->
            if (exception != null){
                Toast.makeText(activity!!, exception.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }else{
                playStationModel.clear()
                snapshot!!.documents.forEach { documentSnapshot ->
                    playStationModel.add(documentSnapshot.toObject(PlayStationModel::class.java)!!)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkConnection(){
        val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)  as ConnectivityManager
        val networkInfo = cm.activeNetwork
        if (networkInfo == null){
            Snackbar.make(activity!!.findViewById(R.id.app_bar_main2), "not connected", Snackbar.LENGTH_INDEFINITE).show()
        }
    }
}