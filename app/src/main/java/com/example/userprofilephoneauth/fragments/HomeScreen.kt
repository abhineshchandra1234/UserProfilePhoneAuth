package com.example.userprofilephoneauth.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.userprofilephoneauth.R
import com.example.userprofilephoneauth.databinding.ActivityMainBinding
import com.example.userprofilephoneauth.databinding.FragmentHomeScreenBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_home_screen.*
import java.util.concurrent.TimeUnit


class HomeScreen : Fragment(R.layout.fragment_home_screen) {
    private lateinit var binding: FragmentHomeScreenBinding
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeScreenBinding.bind(view)	//replace setContentView(R.layout.activity_main) with these two lines

        auth=FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        if(currentUser != null) {
//            startActivity(Intent(applicationContext, HomeActivity::class.java))
//            finish()
            //currentUser.phoneNumber
            //Log.d("TAG", "current user no is ${currentUser.phoneNumber}")
            findNavController().navigate(R.id.action_homeScreen_to_newUser)
        }

        binding.btnSendOtp.setOnClickListener {
            Log.d("Main", "btn send is clicked")
            login()
        }

        binding.btnVerifyOtp.setOnClickListener {
            verifyOtp()
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                startActivity(Intent(applicationContext, HomeActivity::class.java))
//                finish()

                findNavController().navigate(R.id.action_homeScreen_to_newUser)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(context, "Failed $e", Toast.LENGTH_LONG).show()
                Log.d("Main", "Failed $e")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

//                var intent = Intent(applicationContext,Verify::class.java)
//                intent.putExtra("storedVerificationId",storedVerificationId)
//                ContextCompat.startActivity(intent)
            }
        }
    }

    private fun verifyOtp() {
        var otp= binding.etEnterOtp.text.toString()
        if(!otp.isEmpty()){
            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                storedVerificationId.toString(), otp)
            signInWithPhoneAuthCredential(credential)
        }else{
            Toast.makeText(context,"Enter OTP",Toast.LENGTH_SHORT).show()
        }
    }

    private fun login() {
        var number=binding.etEnterNo.text.toString()

        if(!number.isEmpty()){
            number="+91"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(context,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context as Activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_homeScreen_to_newUser)
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(context,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}