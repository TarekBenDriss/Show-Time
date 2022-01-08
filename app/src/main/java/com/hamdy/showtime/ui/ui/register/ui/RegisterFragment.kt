package com.hamdy.showtime.ui.ui.register.ui

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.RegisterFragmentBinding
import com.hamdy.showtime.ui.util.LOGIN_KEY
import com.hamdy.showtime.ui.util.SHARED_PREF_ID
import com.hamdy.showtime.ui.util.SUCCESSFUL_RESPONSE_VALUE

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding : RegisterFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.signinLabel.setOnClickListener {
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
            if(it == SUCCESSFUL_RESPONSE_VALUE){
                val sharedPreferences: SharedPreferences =
                    context?.getSharedPreferences(SHARED_PREF_ID, Context.MODE_PRIVATE)!!
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putBoolean(LOGIN_KEY,true)
                editor.apply()
                editor.commit()
                findNavController().popBackStack()
            }
        })
    }
    private fun validation(name: String, mail: String, pass: String): Boolean {
        var valid = true
        val userName =binding.userName
        val email =binding.emailRegister
        val password =binding.passwordRegister
        if (name.isEmpty()) {
            valid = false
            userName.error = getString(R.string.signup_username_error)
        } else {
            userName.error = null
        }
        if (mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            valid = false
            email.error = getString(R.string.signup_email_error)
        } else {
            email.error = null
        }
        if (pass.isEmpty()) {
            valid = false
            password.error = getString(R.string.signup_password_error)
        } else {
            password.error = null
        }
        return valid
    }

}