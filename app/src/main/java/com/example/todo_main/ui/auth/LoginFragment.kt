package com.example.todo_main.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo_main.MainActivity
import com.example.todo_main.R
import com.example.todo_main.util.*

@ForgotPasswordFragment.AndroidEntryPoint
class LoginFragment : Fragment() {

    val TAG: String = "LoginFragment"
    private var _binding: FragmentLoginBinding? = null

    class FragmentLoginBinding {

        val root: View?
        val login: Any
            get() {
                TODO()
            }
    }

    private val binding get() = _binding!!
    val viewModel: AuthViewModel<Any?> by viewModels()

    private fun viewModels(): ReadOnlyProperty<LoginFragment, AuthViewModel<Any?>> = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.login.setOnClickListener {
            if (validation()) {
                viewModel.login(
                    email = binding.username.text.toString(),
                    password = binding.password.text.toString()
                )
            }
        }
        binding.forgotPassword.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, ForgotPasswordFragment())
                ?.commit();
        }
    }

    private fun observer(){
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.loading.show()
                }
                is UiState.Failure -> {
                    binding.loading.hide()
                    snackbar("error")
                }
                is UiState.Success -> {
                    binding.loading.hide()
                    snackbar(state.data)
                    (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
                    startActivity(Intent(activity, MainActivity::class.java))
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.username.text.isNullOrEmpty()){
            isValid = false
            snackbar(getString(R.string.enter_email))
        }else{
            if (!binding.username.text.toString().isValidEmail()){
                isValid = false
                snackbar(getString(R.string.invalid_login))
            }
        }
        if (binding.password.text.isNullOrEmpty()){
            isValid = false
            snackbar(getString(R.string.enter_password))
        }else{
            if (binding.password.text.toString().length < 8){
                isValid = false
                snackbar(getString(R.string.invalid_login))
            }
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}