package com.example.projetreveil

import android.R.attr.text
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetreveil.ui.theme.ProjetReveilTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Main Activity" , "OnCreate")
        enableEdgeToEdge()
        setContent {
            ProjetReveilTheme {
                Scaffold(modifier = Modifier.fillMaxSize() ,  ) { innerPadding ->
                    RegisterPage(
                        name = "Android",
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


@Composable
fun Logo(size : Float){
    val imageModifier = Modifier.size(size.dp).clip(CircleShape)
    Image(painter = painterResource(R.drawable.penguin) ,"LogoPenguin" , contentScale = ContentScale.Crop,  modifier = imageModifier)

}

@Composable
fun RegisterPage(name: String, modifier: Modifier = Modifier){
    var mdp : String = "";
    var pseudo : String = "";
    var alignment : Modifier = Modifier.fillMaxWidth()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = alignment ){
        //Spacer()
        Logo(45f)
        RegisterTextField("Pseudo")

        RegisterTextField("Mot de Passe")

        Button({} ) {
            Text(text = "Valider")
        }
    }
}

@Composable
fun RegisterTextField(text : String) : String {
    var textWriten : String = "";
    Text(text = text)
    TextField(value = textWriten, onValueChange = {textWriten = it})
    return textWriten
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





