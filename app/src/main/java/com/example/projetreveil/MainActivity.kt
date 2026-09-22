package com.example.projetreveil

import android.R.attr.text
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
    var alignment : Modifier;

    Column(horizontalAlignment = Alignment.CenterHorizontally ){
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

    R : raccourci pour dire qu'on dall une ressource


    Les lambdas :
       quand un seul param on utilise it pr le désigner

*/


