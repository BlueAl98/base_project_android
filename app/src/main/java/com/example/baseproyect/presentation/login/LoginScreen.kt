package com.example.baseproyect.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.baseproyect.R
import com.example.baseproyect.domain.exeptions.ResultState
import com.example.baseproyect.presentation.composables.GeneralTextField
import com.example.baseproyect.presentation.composables.PaswordTextField
import com.example.baseproyect.presentation.navigation.HomeScreen
import com.example.baseproyect.presentation.navigation.LoginScreen
import com.example.baseproyect.presentation.ui.theme.BaseProyectTheme
import com.example.baseproyect.utils.ErrorHandler


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    BaseProyectTheme {
        Scaffold (
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        titleContentColor = Color.White,
                    ),
                    title = {
                        Text("Login")
                    }
                )
            }

        ) { innerPading ->
            BodyLoginScreen(innerPading, navController)
        }
    }
}


@Composable
fun BodyLoginScreen( paddingValues: PaddingValues, navController: NavController  ,viewModel: LoginViewModel = hiltViewModel() ) {

    val state  by viewModel.login.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorThrowable by remember { mutableStateOf(Throwable()) }
    var email by remember { mutableStateOf(TextFieldValue("info@gastiganto.com")) }
    var password by remember { mutableStateOf(TextFieldValue("secret")) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetState()
        }
    }

    if (showErrorDialog)
        ErrorHandler.ParseError(onDismiss = { showErrorDialog = false }, e = errorThrowable)

    if(isLoading) {
        CircularProgressIndicator()
    }

    //Manage States error, successful and loading
    when (val loginstate = state ) {
        is ResultState.Start -> {
            isLoading = false
        }
        is ResultState.Loading -> {
            LaunchedEffect(Unit) {
                isLoading = true
            }
        }

        is ResultState.Success -> {
            LaunchedEffect(Unit) {
                isLoading = false
                viewModel.saveToken(loginstate.data.token)
                navController.navigate(HomeScreen){
                    popUpTo(LoginScreen) {
                        inclusive = true
                    }
                }
            }
        }

        is ResultState.Failure -> {
            LaunchedEffect(Unit) {
                errorThrowable = loginstate.throwable
                showErrorDialog = true
                isLoading = false
            }
        }
    }


    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        //Image logo here
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "logo"
        )

        GeneralTextField(
            modifier = Modifier,
            text = email,
            onTextChange = { newText -> email = newText },
            enabled = !isLoading
        )

        PaswordTextField(
            modifier = Modifier,
            text = password,
            onTextChange = { newText -> password = newText },
            enabled = !isLoading
        )

        Button(
            onClick = { viewModel.login(email.text, password.text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = (!isLoading) && (email.text.isNotEmpty() && password.text.isNotEmpty()),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Iniciar sesión")
        }

    }

}
