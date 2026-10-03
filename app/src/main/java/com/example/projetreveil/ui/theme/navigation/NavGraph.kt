package com.example.projetreveil.ui.theme.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetreveil.ui.theme.screens.RegisterPage
import com.example.projetreveil.ui.theme.screens.WelcomePage

@Composable
fun MonApp(modifier: Modifier){
    val navController = rememberNavController()

    NavHost(
        navController,
        "register"
    ){
        composable("register"){
            RegisterPage(name = "Register Page", modifier = modifier, onConfirmClicked = { navController.navigate("welcome") })
        }

        composable("welcome"){
            WelcomePage(name = "Welcome Page", modifier = modifier)
        }

    }
}