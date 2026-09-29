package com.example.projetreveil.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetreveil.ui.theme.screens.RegisterPage

@Composable
fun MonApp(){
    val navController = rememberNavController()

    NavHost(
        navController,
        "register"
    ){
        composable("register"){
            RegisterPage("register")
        }

        composable("welcome"){

        }

    }
}