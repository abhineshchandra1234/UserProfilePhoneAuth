package com.example.userprofilephoneauth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.phoneauthentication.data.models.User
import com.example.phoneauthentication.viewModels.UserViewModel
import com.example.userprofilephoneauth.R
import com.example.userprofilephoneauth.databinding.FragmentNewUserBinding
import com.google.firebase.auth.FirebaseAuth

class NewUser : Fragment(R.layout.fragment_new_user) {
    private lateinit var binding: FragmentNewUserBinding
    lateinit var auth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewUserBinding.bind(view)
        auth=FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Toast.makeText(context, "phone no is ${currentUser?.phoneNumber}", Toast.LENGTH_SHORT).show()
        Log.d("Main", "current user phone no is ${currentUser?.phoneNumber}")

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            registerNewUser()
        }
    }

    private fun registerNewUser() {
        val name = binding.etEnterName.text.toString().trim()
        val email = binding.etEnterEmail.text.toString().trim()
        val currentUser = auth.currentUser
        val userNumber = currentUser?.phoneNumber
        if (!(name.isEmpty() || email.isEmpty())) {
            val user = userNumber?.let { User(it,name,email) }
            if (user != null) {
                userViewModel.addUser(user)
            }
            Toast.makeText(context, "User added successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_newUser_to_userDetails)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}