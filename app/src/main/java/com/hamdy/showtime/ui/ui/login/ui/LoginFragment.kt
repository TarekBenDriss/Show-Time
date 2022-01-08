package com.hamdy.showtime.ui.ui.login.ui

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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.LoginFragmentBinding
import com.hamdy.showtime.ui.util.LOGIN_KEY
import com.hamdy.showtime.ui.util.SHARED_PREF_ID
import com.hamdy.showtime.ui.util.SUCCESSFUL_RESPONSE_VALUE

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding :LoginFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.signup.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment,null,null,null)
        }
        binding.signButton.setOnClickListener{
            val email: String = binding.emailLogin.text.toString().trim()
            val password: String =binding.password.text.toString().trim()
            if (validation(email, password)) {
                loginUser(email, password)
            }
        }
    }
    private fun loginUser(email: String, password: String)
    {
        val dialog =  Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
        viewModel.login(email,password)
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            dialog.cancel()
            if(it == SUCCESSFUL_RESPONSE_VALUE){
                val sharedPreferences: SharedPreferences =
                    context?.getSharedPreferences(SHARED_PREF_ID,Context.MODE_PRIVATE)!!
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putBoolean(LOGIN_KEY,true)
                editor.apply()
                editor.commit()
                findNavController().popBackStack()
            }
        })

//        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener {
//            if(it.isSuccessful){
//                Toast.makeText(context, "success ", Toast.LENGTH_SHORT).show()
//                dialog.cancel()
//
//            }else{
//                Toast.makeText(context, "failed in complet", Toast.LENGTH_SHORT).show()
//                dialog.cancel()
//
//            }
//        }.addOnFailureListener {
//            Toast.makeText(context, "failed ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
//           dialog.cancel()
//        }

    }
    private fun validation(mail: String, pass: String): Boolean {
        var valid = true
        val email =binding.emailLogin
        val password =binding.password
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