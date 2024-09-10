package com.example.baseproyect.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.baseproyect.presentation.login.LoginScreen
import com.example.baseproyect.presentation.login.LoginViewModel
import com.example.baseproyect.presentation.navigation.Navigation
import com.example.baseproyect.presentation.ui.theme.BaseProyectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewmodel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaseProyectTheme {
                Navigation(loginViewmodel)
            }
        }
    }
}

