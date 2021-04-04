package com.example.userprofilephoneauth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.phoneauthentication.viewModels.UserViewModel
import com.example.userprofilephoneauth.R
import com.example.userprofilephoneauth.databinding.FragmentUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UserDetails : Fragment(R.layout.fragment_user_details) {
    private lateinit var binding: FragmentUserDetailsBinding
    lateinit var auth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel
    private lateinit var number : String
    private val args by navArgs<UserDetailsArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUserDetailsBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        checkNumberDetails()

        binding.btnLogoutUserDetails.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_userDetails_to_homeScreen)
        }
    }

    private fun checkNumberDetails() {
            binding.tvUserNameUserDetails.setText(args.currentUser.user_name)
            binding.tvUserEmailUserDetails.setText(args.currentUser.user_email)
            binding.tvUserNumberlUserDetails.setText(args.currentUser.user_name)
    }
}