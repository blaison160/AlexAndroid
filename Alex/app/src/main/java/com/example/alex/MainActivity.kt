package com.example.alex

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.example.alex.ui.theme.AlexTheme
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlexTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    DisplayMainPage(modifier)
}

@Composable
fun DisplayMainPage(modifier: Modifier = Modifier
    .padding(10.dp)){
    val context = LocalContext.current
    val health = remember { mutableIntStateOf(6) }
    val version = remember { mutableDoubleStateOf(0.0) }
    val hunger = remember { mutableIntStateOf(10)}
    val currentMood = remember { mutableStateOf(Mood.NEUTRAL) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DisplayHeart(health, modifier)
            Column{
                DisplayFace(currentMood, context, modifier)
                DisplayInteractionsMenu(context, modifier)
            }
            DisplayVersionBar(version, modifier)
            DisplayHungerBar(hunger, modifier)
        }
    }
}

@Composable
fun DisplayHeart(health: MutableIntState, modifier: Modifier) {

}

@Composable
fun DisplayVersionBar(version: MutableDoubleState, modifier: Modifier) {

}

@Composable
fun DisplayHungerBar(hunger: MutableIntState, modifier: Modifier){

}

@Composable
fun DisplayFace(currentMood: MutableState<Mood>, context: Context, modifier: Modifier) {
    val layout = LinearLayout(context)
    layout.orientation = LinearLayout.VERTICAL

    val imageViewGif = ImageView(context)

    // Définissez les paramètres de layout pour l'ImageView
    val layoutParams = LinearLayout.LayoutParams(300,300)
    imageViewGif.layoutParams = layoutParams

    layout.addView(imageViewGif)


    Glide.with(context)
        .asGif()
        .load(R.drawable.background)  // Remplacez 'my_gif' par le nom de votre fichier GIF
        .into(imageViewGif);
}

@Composable
fun DisplayInteractionsMenu(context: Context, modifier: Modifier) {
    Row{
        Button(onClick = {}) {
            //Icon()//icone apple
            Text(" "+getString(context, R.string.feedButton))
        }
        Button(onClick = {}){
            //Icon()//icone terminal
            Text(" "+ getString(context, R.string.cmdButton))
        }
    }
}

@Preview
@Composable
fun GreetingsPreview(){
    AlexTheme {
        Greeting()
    }
}