package com.example.baseproyect.presentation.splash

import com.example.baseproyect.R
import com.example.baseproyect.presentation.login.LoginViewModel
import com.example.baseproyect.presentation.navigation.HomeScreen
import com.example.baseproyect.presentation.navigation.LoginScreen
import com.example.baseproyect.presentation.navigation.SplashScreen
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, loginViewmodel: LoginViewModel) {

    //TOKEN verification
    LaunchedEffect(key1 = Unit) {
        loginViewmodel.getToken()
    }

    val scaleAnimation: Animatable<Float, AnimationVector1D> = remember { Animatable(initialValue = 0f) }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        navController = navController,
        durationMillisAnimation = 1200,
        delayScreen = 500L,
        loginViewmodel
    )

    DesignSplashScreen(
        imagePainter = painterResource(id = R.drawable.ic_launcher_foreground),
        scaleAnimation = scaleAnimation
    )
}

@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    navController: NavController,
    durationMillisAnimation: Int,
    delayScreen: Long,
    loginViewmodel: LoginViewModel
) {

    val hasToken by loginViewmodel.token.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        scaleAnimation.animateTo(
            targetValue = 0.5F,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(3F).getInterpolation(it)
                }
            )
        )

        delay(timeMillis = delayScreen)

        if(hasToken != ""){
            navController.navigate(HomeScreen){
                popUpTo(SplashScreen) {
                    inclusive = true
                }
            }
        }else{
            navController.navigate(LoginScreen){
                popUpTo(SplashScreen) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun DesignSplashScreen(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    scaleAnimation: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = imagePainter,
                contentDescription = "Splash Screen",
                modifier = modifier
                    .size(350.dp)
                    .scale(scale = scaleAnimation.value),
            )
        }
    }
}