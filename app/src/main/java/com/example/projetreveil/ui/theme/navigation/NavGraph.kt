package com.example.projetreveil.ui.theme.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetreveil.ui.theme.DataStoreSettingsApp
import com.example.projetreveil.ui.theme.screens.AlarmRingingPage
import com.example.projetreveil.ui.theme.screens.RegisterPage
import com.example.projetreveil.ui.theme.screens.SetAlarmPage
import com.example.projetreveil.ui.theme.screens.SetGroupePage
import com.example.projetreveil.ui.theme.screens.SettingsPage
import com.example.projetreveil.ui.theme.screens.WelcomePage
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel

@Composable
fun MonApp(
    modifier: Modifier,
    context: Context,
    dataStore: DataStoreSettingsApp,
    alarmsViewModel: AlarmsViewModel,
    startingPage: String,
    currentAlarmRingingID: Int
){
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = startingPage

    ){
        composable("register"){
            RegisterPage(name = "Register Page", modifier = modifier,
                onConfirmClicked = { navController.navigate("welcome"){
                    popUpTo("register"){
                        inclusive = true
                    }
                } })
        }

        composable("welcome"){
            WelcomePage(name = "Welcome Page",
                context = context,
                modifier = modifier,
                alarmsViewModel = alarmsViewModel,
                onGoToGroupClicked = { navController.navigate("group") },
                onGoToConfigureAlarmClicked = { navController.navigate("set-alarm") },
                onGoToSettingsClicked = { navController.navigate("settings") },
                settingsDataStore = dataStore
            )
        }

        composable("set-alarm"){
            SetAlarmPage(
                name = "Set Alarm Page",
                context = context,
                modifier = modifier,
                alarmsViewModel,
                onConfirmClicked = { navController.navigate("welcome") },
                onCancelClicked = { navController.navigate("welcome") }
            )
        }

        composable("group"){
            SetGroupePage(name = "Groupe Page",
                onGoToMenuclicked = { navController.navigate("welcome") },
                onGoToConfigureAlarmClicked = { navController.navigate("set-alarm") },
                onGoToSettingsClicked = { navController.navigate("settings") }
                )
        }

        composable(route = "settings"){
            SettingsPage(name = "Settings", modifier = modifier,
                onGoBackClicked = { navController.popBackStack() },
                settingsDataStore = dataStore
                )
        }

        composable(route = "alarm-ringing"){
            AlarmRingingPage(name = "Alarm Ringing",
                modifier = modifier,
                context = context,
                alarmsViewModel = alarmsViewModel,
                alarmTime = "08:30",
                onSnoozeClicked = { navController.navigate("welcome"){
                    popUpTo("alarm-ringing"){
                        inclusive = true
                    }
                } },
                onStopClicked = { navController.navigate("welcome"){
                    popUpTo("alarm-ringing"){
                        inclusive = true
                    }
                } },
                alarmID = currentAlarmRingingID
            )
        }

    }
}