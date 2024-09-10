package com.example.baseproyect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproyect.presentation.login.LoginViewModel
import kotlinx.serialization.Serializable
import com.example.baseproyect.presentation.splash.SplashScreen
import com.example.baseproyect.presentation.homeScreen.HomeScreen
import com.example.baseproyect.presentation.login.LoginScreen


//Argumentos
@Serializable
object SplashScreen

@Serializable
object HomeScreen

@Serializable
object LoginScreen

@Composable
fun Navigation( loginViewmodel: LoginViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashScreen
    ) {
        composable<SplashScreen> {
            SplashScreen(
                navController = navController,
                loginViewmodel = loginViewmodel
            )
        }

        composable<LoginScreen> {
            LoginScreen(navController)
        }

        composable<HomeScreen> {
           HomeScreen(navController)
        }


    }
}