package com.example.userprofilephoneauth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.phoneauthentication.data.models.User
import com.example.phoneauthentication.viewModels.UserViewModel
import com.example.userprofilephoneauth.databinding.FragmentLoginScreenBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class LoginScreen : Fragment(R.layout.fragment_login_screen) {
    private lateinit var binding: FragmentLoginScreenBinding
    private lateinit var userViewModel: UserViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginScreenBinding.bind(view)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        binding.btnLogin.setOnClickListener {
            userValidation()
        }

        binding.btnRegisterLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreen_to_newUser)
        }
    }

    private fun userValidation() {
        var a = emptyList<User>()
        var number = binding.etPhoneLogin.text.trim().toString()
        var email = binding.etEmailLogin.text.trim().toString()
        val job = GlobalScope.launch {
            a = userViewModel.getNumberDetails(number)
        }
        runBlocking {
            job.join()
            job.cancel()

            if ((number == a.get(0).user_number) && (email == a.get(0).user_email)) {
                findNavController().navigate(R.id.action_loginScreen_to_userDetails)
            } else {
                Toast.makeText(context, "user is not valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}