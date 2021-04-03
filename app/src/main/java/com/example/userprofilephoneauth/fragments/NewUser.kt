package com.example.userprofilephoneauth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.phoneauthentication.data.models.User
import com.example.phoneauthentication.viewModels.UserViewModel
import com.example.userprofilephoneauth.R
import com.example.userprofilephoneauth.TestViewModel
import com.example.userprofilephoneauth.databinding.FragmentNewUserBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class NewUser : Fragment(R.layout.fragment_new_user) {
    private lateinit var binding: FragmentNewUserBinding
    lateinit var auth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel
    private lateinit var userList : List<User>
    private lateinit var number : String
    lateinit var viewModel: TestViewModel
    lateinit var userExist: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewUserBinding.bind(view)
        auth=FirebaseAuth.getInstance()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)




        binding.btnRegister.setOnClickListener {
            registerNewUser()
        }

        binding.btnLogoutNewUser.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_newUser_to_homeScreen)
        }

    }


//    private fun addUser() {
//        for (i in 1..5) {
//            var user = User("000$i","employee","email")
//            userViewModel.addUser(user)
//        }
//    }

    private fun checkNumberStatus() {
        GlobalScope.launch {
            Log.d("Main", "value is ${userViewModel.checkNumber(number)} ")
        }
    }

    private fun registerNewUser() {
        val name = binding.etEnterName.text.toString().trim()
        val email = binding.etEnterEmail.text.toString().trim()
        val number = binding.etEnterNumber.text.toString().trim()
        if (!(name.isEmpty() || email.isEmpty())) {
            val user = User(number,name,email)
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