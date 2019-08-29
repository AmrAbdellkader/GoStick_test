package com.example.gostick_test.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gostick_test.Main2Activity
import com.example.gostick_test.R
import kotlinx.android.synthetic.main.lay_frag_language.*
import java.util.*

class FragLanguage : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_frag_language, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        btn_language_arabic.setOnClickListener {
            setLocate("ar")
            sharedPreferences("Arabic")
            startActivity(Intent(activity!!, Main2Activity::class.java))
        }

        btn_language_english.setOnClickListener {
            setLocate("en")
            sharedPreferences("English")
            startActivity(Intent(activity!!, Main2Activity::class.java))
        }
    }

    private fun sharedPreferences(lang: String){
        activity!!.getSharedPreferences("sf", Context.MODE_PRIVATE).edit().putString("language", lang).apply()
    }

    private fun setLocate(lang:String){
        Locale.setDefault(Locale(lang))
        val config = Configuration()
        config.setLocale(Locale(lang))
        //activity!!.resources.configuration.setLocale(Locale(lang))
        //DisplayMetrics().setTo(resources.displayMetrics)
        activity!!.resources.updateConfiguration(config, activity!!.resources.displayMetrics)
    }

    private fun setLocate1(lang: String){
        val conf = resources.configuration
        conf.setLocale(Locale(lang))
        val metrics = DisplayMetrics()
        metrics.setTo(resources.displayMetrics)
    }
}