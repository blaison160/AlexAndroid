package com.example.alex

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.example.alex.alexMood.MoodEnum
import com.example.alex.alexMood.Mood
import com.example.alex.alexMood.Mood.getMoodRepresentation
import com.example.alex.ui.theme.AlexTheme


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
fun DisplayMainPage(modifier: Modifier = Modifier){
    val context = LocalContext.current
    val health = rememberSaveable { mutableIntStateOf(6) }
    val version = rememberSaveable { mutableIntStateOf(30) }
    val hunger = rememberSaveable { mutableIntStateOf(10)}
    val currentMood = rememberSaveable { mutableStateOf(MoodEnum.NEUTRAL) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DisplayHeart(health, modifier)
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                DisplayFace(currentMood, context, modifier)
                DisplayInteractionsMenu(currentMood,context, modifier)
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
fun DisplayVersionBar(version: MutableIntState, modifier: Modifier) {

}

@Composable
fun DisplayHungerBar(hunger: MutableIntState, modifier: Modifier){

}

@Composable
fun DisplayFace(currentMood: MutableState<MoodEnum>, context: Context, modifier: Modifier) {
    val expression = getMoodRepresentation(currentMood.value)
    Box(
        modifier.fillMaxHeight(0.25f)
    ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "background",
            modifier.fillMaxSize()
        )
        Image(
            painter = expression,
            contentDescription = "Alex's face",
            modifier.fillMaxSize()
        )
    }
}

@Composable
fun DisplayInteractionsMenu(currentMood: MutableState<MoodEnum>,context: Context, modifier: Modifier) {
    Row{
        Button(onClick = {
            val initialMood = currentMood.value
            currentMood.value = MoodEnum.NEUTRAL_HAPPY //get the happier face
            val handler = Handler(Looper.getMainLooper())
            val runnable = Runnable{
                currentMood.value =initialMood
            }
            handler.postDelayed(runnable,2000)
        },
            shape = RectangleShape,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.food_apple),
                contentDescription = "apple icon",
                tint = MaterialTheme.colorScheme.tertiary
            )

        }
        Button(onClick = {},
            shape = RectangleShape,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_terminal_24),
                contentDescription = "terminal icon",
                tint = MaterialTheme.colorScheme.tertiary
            )
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