package com.example.projetreveil

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetreveil.ui.theme.ProjetReveilTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Main Activity" , "OnCreate")
        enableEdgeToEdge()
        setContent {
            ProjetReveilTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(){
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetReveilTheme {
        Greeting("Android")
    }
}

/*
OnStart(){}
OnCreate()
OnResume(){}

OnDestroy(){}

Y'a différent log i : info  log w : warning log d: debug log e : erreur

Content provider (partage données structurées entre appli, content provider formé de base
Intent Intention d'action ? action mais pas sûr qu'elle va arriver
Jetpack Compose : truc dédié aux interfaces
    à l'intérieur d'activity avec setContent{}
    O décrit état de l'interface et compose affiche et màj
    @composable c'est un élément(fct) réutilisable (genre un préfab)
    divers elem de base test button, image déjà fournis  Text() Button() Image()

    ensuite on doit positionner avec Column Row et Box (imbricables ofc) et ca s'orga ainsi (box ca s'empile mais on controle comment
    On leur fout des modif pour décider des esapces des paddings, d'être responsive etc etc bla bla dev web
    on a plein de méthodes pr ça (lien de la list sur le diap)


*/


