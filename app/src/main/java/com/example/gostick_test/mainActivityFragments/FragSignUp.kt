package com.example.gostick_test.mainActivityFragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gostick_test.Main2Activity
import com.example.gostick_test.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.lay_frag_sign_up.*

class FragSignUp: Fragment(),TextWatcher {

    private val auth              by lazy { FirebaseAuth.getInstance() }
    private val fireStore         by lazy { FirebaseFirestore.getInstance() }
    private val signUpEmail       by lazy { edt_sign_up_email.text.toString().trim()}
    private val signUpPassword    by lazy {edt_sign_up_password.text.toString().trim()}
    private val signUpAge         by lazy {edt_sign_up_age.text.toString().trim()}
    private val signUpPhoneNumber by lazy {edt_sign_up_phone.text.toString().trim()}
    private val signUpUserName    by lazy {edt_sign_up_user_name.text.toString().trim()}
    private val user              by lazy {hashMapOf<String, Any>(
        "email" to signUpEmail,
        "password" to signUpPassword,
        "age" to signUpAge,
        "phoneNumber" to signUpPhoneNumber,
        "userName" to signUpUserName
    )}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lay_frag_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edt_sign_up_email.addTextChangedListener(this)
        edt_sign_up_password.addTextChangedListener(this)
        edt_sign_up_age.addTextChangedListener(this)
        edt_sign_up_user_name.addTextChangedListener(this)
        edt_sign_up_phone.addTextChangedListener(this)

        txt_sign_up_sign_in.setOnClickListener {
            navigateToFragLogIn()
        }
        txt_sign_up_signup.setOnClickListener {
            if(!checkUserNameValidation()
                ||!checkEmailValidation()
                ||!checkPhoneNumberValidation()
                ||!checkAgeValidation()
                ||!checkPasswordValidation()
            ) {
                return@setOnClickListener
            }
            signUp()
        }
    }

    private fun navigateToFragLogIn(){
        activity!!.supportFragmentManager.beginTransaction().add(R.id.activity_main, FragLogIn())
    }

    private fun navigateToMain2Activity(){
        val intent = Intent(activity!!, Main2Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity!!.startActivity(intent)
    }

    private fun signUp(){
        progressBar2.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(signUpEmail, signUpPassword).addOnCompleteListener { task ->

            fireStore.collection("users").document(auth.currentUser?.email.toString()).set(user)
            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(signUpUserName).build()
            auth.currentUser?.updateProfile(profileUpdates)?.addOnFailureListener{ exception ->
                Toast.makeText(activity!!, exception.message, Toast.LENGTH_LONG).show()
            }

            if (task.isSuccessful) {
                progressBar2.visibility = View.GONE
                navigateToMain2Activity()
            }else {
                progressBar2.visibility = View.GONE
                Toast.makeText(activity!!, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun checkEmailValidation(): Boolean{

        if (signUpEmail.isEmpty()){
            edt_sign_up_email.error = "Field Can't be empty"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches()){
            edt_sign_up_email.error = "Enter valid email"
            return false
        }else{
            edt_sign_up_email.error = null
            return true
        }
    }

    private fun checkPasswordValidation(): Boolean{

        if (signUpPassword.isEmpty()){
            edt_sign_up_password.error = "Field Can't be empty"
            return false
        }
        if (signUpPassword.length < 6) {
            edt_sign_up_password.error = "Enter more than 6 characters"
            return false
        } else{
            edt_sign_up_password.error = null
            return true
        }
    }

    private fun checkAgeValidation(): Boolean{

        if (signUpAge.isEmpty()){
            edt_sign_up_age.error = "Field Can't be empty"
            return false
        }
         else{
            edt_sign_up_age.error = null
            return true
        }
    }

    private fun checkUserNameValidation(): Boolean{

        if (signUpUserName.isEmpty()){
            edt_sign_up_user_name.error = "Field Can't be empty"
            return false
        } else{
            edt_sign_up_user_name.error = null
            return true
        }
    }

    private fun checkPhoneNumberValidation(): Boolean{

        if (signUpPhoneNumber.isEmpty()){
            edt_sign_up_phone.error = "Field Can't be empty"
            return false
        }
        if (signUpPhoneNumber.length < 11) {
            edt_sign_up_phone.error = "Enter valid phone number"
            return false
        } else{
            edt_sign_up_phone.error = null
            return true
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        edt_sign_up_email.error = null
        edt_sign_up_password.error = null
        edt_sign_up_age.error = null
        edt_sign_up_user_name.error = null
        edt_sign_up_phone.error = null

        txt_sign_up_signup.isEnabled =
            signUpUserName.isNotEmpty()
                    &&signUpEmail.isNotEmpty()
                    && signUpPhoneNumber.isNotEmpty()
                    && signUpAge.isNotEmpty()
                    && signUpPassword.isNotEmpty()
    }

}