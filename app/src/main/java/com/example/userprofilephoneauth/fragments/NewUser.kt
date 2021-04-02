package com.example.userprofilephoneauth.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.userprofilephoneauth.R
import com.example.userprofilephoneauth.databinding.FragmentNewUserBinding
import com.google.firebase.auth.FirebaseAuth

class NewUser : Fragment(R.layout.fragment_new_user) {
    private lateinit var binding: FragmentNewUserBinding
    lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewUserBinding.bind(view)
        auth=FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Toast.makeText(context, "phone no is ${currentUser?.phoneNumber}", Toast.LENGTH_SHORT).show()
        Log.d("Main", "current user phone no is ${currentUser?.phoneNumber}")
    }
}