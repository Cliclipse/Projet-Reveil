package com.example.projetreveil

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetreveil.ui.theme.ProjetReveilTheme
import com.example.projetreveil.ui.theme.screens.RegisterPage
import com.example.projetreveil.ui.theme.screens.WelcomePage

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Main Activity" , "OnCreate")
        enableEdgeToEdge()
        setContent {
            ProjetReveilTheme {
                Scaffold(modifier = Modifier.fillMaxSize() ,  ) { innerPadding ->
                    /*
                    RegisterPage(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                     */

                    WelcomePage(
                        name = "WelcomePage",
                        modifier = Modifier.padding(innerPadding)
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





