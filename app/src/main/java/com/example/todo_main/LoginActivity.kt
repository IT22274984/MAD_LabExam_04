package com.example.todo_main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.todo_main.databinding.ActivityLoginBinding
import com.example.todo_main.ui.auth.ForgotPasswordFragment
import com.example.todo_main.ui.auth.WelcomeFragment

@ForgotPasswordFragment.AndroidEntryPoint
class LoginActivity<ActivityLoginBinding> : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val also = ActivityLoginBinding.inflate(layoutInflater).also {
            binding = it
        }
        androidx.compose.foundation.layout.Box {
            setContentView(binding.root)
        }

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            loadFragment(WelcomeFragment())
        }

        loadFragment(WelcomeFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}