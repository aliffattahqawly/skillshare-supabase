package com.yourssohail.learnsupabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.yourssohail.learnsupabase.data.model.UserState

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: SupabaseAuthViewModel
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var buttonLogin: Button
    private lateinit var buttonLogout: Button
    private lateinit var textViewStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(SupabaseAuthViewModel::class.java)

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogout = findViewById(R.id.buttonLogout)
        textViewStatus = findViewById(R.id.textViewStatus)

        // Set up click listeners
        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            viewModel.signUp(this, email, password)
        }

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            viewModel.login(this, email, password)
        }

        buttonLogout.setOnClickListener {
            viewModel.logout(this)
        }

        // Observe user state changes
        viewModel.userState.observe(this, { userState ->
            when (userState) {
                is UserState.Loading -> {
                    textViewStatus.text = "Loading..."
                }
                is UserState.Success -> {
                    textViewStatus.text = userState.message
                }
                is UserState.Error -> {
                    textViewStatus.text = userState.message
                }
            }
        })

        // Check if user is already logged in
        viewModel.isUserLoggedIn(this)
    }
}