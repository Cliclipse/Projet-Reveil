package com.example.projetreveil

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.projetreveil.ui.theme.DataStoreSettingsApp
import com.example.projetreveil.ui.theme.ProjetReveilTheme
import com.example.projetreveil.ui.theme.RoomDatas.AlarmDatabase
import com.example.projetreveil.ui.theme.navigation.MonApp
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModelFactory

class MainActivity : ComponentActivity() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("Main Activity" , "OnCreate")

        val alarmsDataBase = Room.databaseBuilder(
            applicationContext,
            AlarmDatabase::class.java,
            "database-alarms"
        ).build()

        val alarmDao = alarmsDataBase.AlarmDao()

        enableEdgeToEdge()
        setContent {

            val dataStore: DataStoreSettingsApp = DataStoreSettingsApp(LocalContext.current)

            val nightMode by dataStore.nightMode.collectAsStateWithLifecycle(initialValue = false)

            ProjetReveilTheme(darkTheme = nightMode) {
                Scaffold(modifier = Modifier.fillMaxSize() ,  ) { innerPadding ->

                    val alarmsViewModel : AlarmsViewModel = viewModel(factory = AlarmsViewModelFactory(alarmDao))

                    MonApp(
                        modifier = Modifier.padding(innerPadding),
                        context = applicationContext,
                        dataStore = dataStore,
                        alarmsViewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Main Activity" , "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Main Activity" , "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Main Activity" , "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Main Activity" , "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Main Activity" , "OnDestroy")
    }
}








/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(){
        Logo()
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(
            text = "Big Fucking Penguinnnnn",
            modifier = modifier
        )
    }
}
*/


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetReveilTheme {
        //Greeting("Android")
    }
}





