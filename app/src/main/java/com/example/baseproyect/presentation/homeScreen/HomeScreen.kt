package com.example.baseproyect.presentation.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baseproyect.presentation.composables.CustomAlertDialog
import com.example.baseproyect.presentation.login.LoginViewModel
import com.example.baseproyect.presentation.navigation.HomeScreen
import com.example.baseproyect.presentation.navigation.LoginScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController:NavController , loginViewmodel: LoginViewModel = hiltViewModel() ) {
    var showConfirmMessage by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedScreen by rememberSaveable { mutableStateOf(DrawerScreen.Home) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            //MENU SIDE
            ModalDrawerSheet {
                Column (modifier = Modifier.width(250.dp)){

                    Spacer(modifier = Modifier.size(10.dp))

                    Icon(modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally),
                        imageVector = Icons.Default.Person ,
                        contentDescription = "",

                    )
                    Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Najib bukele",)
                    HorizontalDivider()
                    DrawerItem(text = "Home", onClick = {
                        selectedScreen = DrawerScreen.Home
                        scope.launch { drawerState.close() }
                    }, icon = Icons.Default.Home )
                    DrawerItem(text = "Play", onClick = {
                        selectedScreen = DrawerScreen.Play
                        scope.launch { drawerState.close() }
                    }, icon = Icons.Default.PlayCircle )
                    DrawerItem(text = "Settings", onClick = {
                        selectedScreen = DrawerScreen.Settings
                        scope.launch { drawerState.close() }
                    }, icon = Icons.Default.Settings )
                }
            }
        },
    ){
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = selectedScreen.name)},
                    actions = {
                        IconButton(onClick = { showConfirmMessage = true },  ) {
                            Icon(imageVector = Icons.Default.Output, contentDescription = "" )
                        }

                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() }  }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                        }
                    }

                )
            }
        ) { padding->
            //Manage different screens
            Column(Modifier.padding(padding)) {
                when (selectedScreen){
                    DrawerScreen.Home -> {
                        Screen1()
                    }
                    DrawerScreen.Play -> {
                        Screen2()
                    }
                    DrawerScreen.Settings -> {
                        Screen3()
                    }
                }
            }

            if (showConfirmMessage){
                CustomAlertDialog(onConfirmation = {
                    loginViewmodel.deleteToken()
                    showConfirmMessage = false
                    navController.navigate(LoginScreen) {
                        popUpTo(HomeScreen) {
                            inclusive = true
                        }
                    }
                }, onDismissRequest = {
                    showConfirmMessage = false
                },
                    dialogText = "¿Desea salir de la aplicación?",
                    dialogTitle = "Cerrar sesión",
                    dismissButtonText = "Cancelar",
                    confirmButtonText = "Salir"
                )
            }
        }

    }
}

@Composable
fun Screen1() {
    println("Screen 1")
    Text( text = "HOME SCREEN", textAlign = TextAlign.Center)
}

@Composable
fun Screen2() {
    Text(text = "PLAY SCREEN")

}

@Composable
fun Screen3() {
    Text(text = "Settings SCREEN")
}


@Composable
fun DrawerItem(text: String, onClick: () -> Unit, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = "" )
        Text(text)
    }
}

//Enum Screens
enum class DrawerScreen {
    Home,
    Play,
    Settings
}