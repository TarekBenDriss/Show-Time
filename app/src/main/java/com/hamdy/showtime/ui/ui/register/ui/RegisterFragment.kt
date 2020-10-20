package com.hamdy.showtime.ui.ui.register.ui

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding : RegisterFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.Signin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment,null,null,null)
        }

        binding.signButton.setOnClickListener{
            val name: String = binding.userName.text.toString().trim()
            val email: String = binding.emailRegister.text.toString().trim()
            val password: String =binding.passwordRegister.text.toString().trim()
            if (validation(name,email, password)) {
                register(name,email, password)
            }
        }
    }
    private fun register(userName: String, email: String, password: String) {
        val dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
        viewModel.register(userName,email, password)
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            dialog.cancel()
        })
    }
    private fun validation(name: String, mail: String, pass: String): Boolean {
        var valid = true
        val userName =binding.userName
        val email =binding.emailRegister
        val password =binding.passwordRegister
        if (name.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
            valid = false
            userName.error = "User name can't be empty"
        } else {
            userName.error = null
        }
        if (mail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            valid = false
            email.error = "Email can't be empty"
        } else {
            email.error = null
        }
        if (pass.isEmpty()) {
            valid = false
            password.error = "Password can't be empty"
        } else {
            password.error = null
        }
        return valid
    }

}