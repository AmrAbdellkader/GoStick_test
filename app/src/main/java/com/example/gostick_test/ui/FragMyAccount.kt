package com.example.gostick_test.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gostick_test.MainActivity
import com.example.gostick_test.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.lay_frag_my_account.*

class FragMyAccount : DialogFragment() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.lay_frag_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    btn_my_account_log_out.setOnClickListener {
        auth.signOut()
        navigateToMainActivity()
    }
        txt_my_account_email_address?.text = auth.currentUser?.email.toString()
        txt_my_account_user_name?.text = auth.currentUser?.displayName.toString()
        btn_my_account_change_password.setOnClickListener {
            navigateToFragChangePassword()
        }
    }

    private fun navigateToFragChangePassword() {
        FragChangePassword().show(activity!!.supportFragmentManager, "pop")
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(activity!!, MainActivity::class.java))
    }
}