package com.example.projetreveil.ui.theme.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projetreveil.ui.theme.DataStoreSettingsApp
import com.example.projetreveil.ui.theme.screens.AlarmRingingPage
import com.example.projetreveil.ui.theme.screens.ModifyAlarmPage
import com.example.projetreveil.ui.theme.screens.NewGroupScreen
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
                onModifySelectedAlarmClicked = {
                    val alarmID = it.id
                    Log.d("ALARMS", "ID to modify sent : $alarmID")
                    navController.navigate("modify-alarm/$alarmID")
                }
            )
        }

        composable("set-alarm"){
            SetAlarmPage(
                name = "Set Alarm Page",
                context = context,
                modifier = modifier,
                alarmsViewModel = alarmsViewModel,
                onConfirmClicked = { navController.popBackStack() },
                onCancelClicked = { navController.popBackStack() }
            )
        }

        composable("modify-alarm/{alarmID}",
            arguments = listOf(
                navArgument("alarmID") {
                    type = NavType.IntType
                }
            )
        ){
            backStackEntry ->

            val alarmID = backStackEntry.arguments?.getInt("alarmID") ?: -1

            Log.d("ALARMS", "ID to modify received : $alarmID")

            ModifyAlarmPage(
                name = "Modify Alarm Page",
                context = context,
                modifier = modifier,
                associatedAlarmID = alarmID,
                alarmsViewModel = alarmsViewModel,
                onConfirmClicked = { navController.popBackStack() },
                onCancelClicked = { navController.popBackStack() }
            )
        }

        composable("group"){
            SetGroupePage(
                onAddGroup = { navController.navigate("createGroup") },
                onGoToMenuclicked = { navController.navigate("welcome") },
                onGoToConfigureAlarmClicked = { navController.navigate("set-alarm") },
                onGoToSettingsClicked = { navController.navigate("settings")
                }
            )
        }

        composable("createGroup"){
            NewGroupScreen(
                onConfirmClicked = {navController.navigate("group") },
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