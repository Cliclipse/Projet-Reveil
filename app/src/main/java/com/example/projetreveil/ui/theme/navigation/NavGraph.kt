package com.example.projetreveil.ui.theme.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetreveil.ui.theme.screens.RegisterPage
import com.example.projetreveil.ui.theme.screens.SetGroupePage
import com.example.projetreveil.ui.theme.screens.SetReveilPage
import com.example.projetreveil.ui.theme.screens.WelcomePage

@Composable
fun MonApp(modifier: Modifier){
    val navController = rememberNavController()

    NavHost(
        navController,
        //"register"
        "register" //temporaire pdt, que je fais la page groupe

    ){
        composable("register"){
            RegisterPage(name = "Register Page", modifier = modifier,
                onConfirmClicked = { navController.navigate("welcome") })
        }

        composable("welcome"){
            WelcomePage(name = "Welcome Page", modifier = modifier,
                onGoToGroupClicked = { navController.navigate("group") },
                onGoToMenuClicked = { navController.navigate("welcome") },
                onGoToConfigureAlarmClicked = { navController.navigate("set-alarm") })
        }

        composable("set-alarm"){
            SetReveilPage(name = "Set Alarm Page", modifier = modifier,
                onConfirmClicked = { navController.navigate("welcome") },
                onCancelClicked = { navController.navigate("welcome") })
        }

        composable("groupePage"){
            SetGroupePage(name = "Groupe Page")
        }

    }
}