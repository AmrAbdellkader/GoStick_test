package com.example.gostick_test.mainActivityFragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gostick_test.Main2Activity
import com.example.gostick_test.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.lay_frag_log_in.*

class FragLogIn: Fragment(), TextWatcher {

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        edt_log_in_email.error = null
        edt_log_in_password.error = null
        btn_log_in_login.isEnabled = edt_log_in_email.text.toString().trim().isNotEmpty() && edt_log_in_password.text.toString().trim().isNotEmpty()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_frag_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edt_log_in_email.addTextChangedListener(this)
        edt_log_in_password.addTextChangedListener(this)
        closeKeyboard()
        txt_log_in_sign_up.setOnClickListener {
            navigateToFragSignUp()
        }
        btn_log_in_login.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkConnection()
            }
            if(!checkEmailValidation() || !checkPasswordValidation() ) {
             return@setOnClickListener
            }
                logIn()
        }
    }

    private fun navigateToFragSignUp(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.activity_main, FragSignUp()).commit()
    }

    private fun navigateToMain2Activity(){
        val intent = Intent(activity!!, Main2Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity!!.startActivity(intent)
    }

    private fun logIn(){
        progressBar.visibility = View.VISIBLE
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(edt_log_in_email.text.toString().trim(), edt_log_in_password.text.toString().trim())
            .addOnCompleteListener {
            if (it.isSuccessful) {
                progressBar.visibility = View.GONE
                navigateToMain2Activity()
            }else {
                progressBar.visibility = View.GONE
                Toast.makeText(activity!!, it.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkEmailValidation(): Boolean{

        if (edt_log_in_email.text.toString().trim().isEmpty()){
            edt_log_in_email.error = "Field Can't be empty"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edt_log_in_email.text.toString().trim()).matches()){
            edt_log_in_email.error = "Enter valid email"
            return false
        }else{
         edt_log_in_email.error = null
            return true
        }
    }

    private fun checkPasswordValidation(): Boolean{

        if (edt_log_in_password.text.toString().trim().isEmpty()){
            edt_log_in_password.error = "Field Can't be empty"
            return false
        }
        if (edt_log_in_password.text.toString().trim().length < 6) {
            edt_log_in_password.error = "Enter more than 6 characters"
            return false
        } else{
            edt_log_in_password.error = null
            return true
        }
    }

    private fun closeKeyboard(){
        val view = activity!!.currentFocus
        if (view != null){
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun checkConnection(){
        val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)  as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkInfo = cm.activeNetwork
            if (networkInfo == null){
                Snackbar.make(activity!!.findViewById(R.id.app_bar_main2), "not connected", Snackbar.LENGTH_INDEFINITE).show()
            }
        }

    }
}