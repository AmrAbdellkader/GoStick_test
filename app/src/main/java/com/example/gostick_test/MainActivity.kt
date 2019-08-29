package com.example.gostick_test

import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.gostick_test.mainActivityFragments.FragLogIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.nav_header_main2.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val user by lazy { auth.currentUser }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLanguage()
    }

    override fun onStart() {
        super.onStart()

        if (user == null) {

            navigateToFragLogIn()
        }
        else{
            navigateToMain2Activity()
        }
    }

    private fun navigateToMain2Activity(){
        intent = Intent(this, Main2Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun navigateToFragLogIn(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.activity_main, FragLogIn())
            .commit()
    }

    private fun setLanguage(){
        Log.d("Tag", "setLanguage")
        val sharedPreference = getSharedPreferences("sf", MODE_PRIVATE)
        val language = sharedPreference.getString("language", "").toString()
        if (language == "Arabic"){ setLocate("ar") }
        else if (language == "English"){ setLocate("en")
        }
    }

    private fun setLocate(lang:String){
        Locale.setDefault(Locale(lang))
        val config = Configuration()
        config.setLocale(Locale(lang))
        @Suppress("DEPRECATION")
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        Log.d("Tag", "setLocate")
    }
}
