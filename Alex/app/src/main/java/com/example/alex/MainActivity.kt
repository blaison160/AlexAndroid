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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alex.management.Health.getHealthRepresentation
import com.example.alex.management.MoodEnum
import com.example.alex.management.Mood.getMoodRepresentation
import com.example.alex.management.Mood
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
    val maxSizeVersionBar = 30
    val versionSupportTime = rememberSaveable { mutableIntStateOf(maxSizeVersionBar) }
    val maxSizeHungerBar = 10
    val hunger = rememberSaveable { mutableIntStateOf(maxSizeHungerBar)}
    val currentMood = rememberSaveable { mutableStateOf(MoodEnum.NEUTRAL) }
    currentMood.value = Mood.getMoodFromHealth(health.intValue)
    val shell = rememberSaveable { mutableStateOf(false) }
    val shellStrings = rememberSaveable { mutableListOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = modifier
                    .fillMaxWidth(0.5F)
            ) {
                DisplayFace(currentMood, modifier)
            }
            Column(
                modifier = modifier
                    .fillMaxWidth(0.5F)
                    .padding(2.dp)
            ){
                DisplayHeart(health, modifier)
                DisplayHungerBar(maxSizeHungerBar, hunger, modifier)
                DisplayVersionBar(maxSizeVersionBar, versionSupportTime, modifier)
            }
        }
        DisplayInteractionsMenu(currentMood, context, health, hunger, versionSupportTime, modifier)

        DisplayActivity(currentMood, context, versionSupportTime, shell, modifier)
    }
}



@Composable
fun DisplayHeart(health: MutableIntState, modifier: Modifier) {
    val heart = getHealthRepresentation(health.intValue)
    Box(
        modifier = modifier.fillMaxHeight(0.3F)
    ) {
        Image(
            painter = heart,
            contentDescription = "heart",
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun DisplayHungerBar(maxSizeHungerBar: Int, hunger: MutableIntState, modifier: Modifier) {
    Box(

        modifier.wrapContentSize()
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
    ) {
        LinearProgressIndicator(
            progress = { hunger.intValue.toFloat() / maxSizeHungerBar },
            color = Color(0xFFFF9800),
            trackColor = MaterialTheme.colorScheme.tertiary,
            strokeCap = StrokeCap.Square,
            drawStopIndicator = {},
            modifier = modifier.height(10.dp)
        )
    }
}

@Composable
fun DisplayVersionBar(maxSizeVersionBar: Int, versionSupportTime: MutableIntState, modifier: Modifier) {
    Box(

        modifier.wrapContentSize()
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
    ) {
        LinearProgressIndicator(
            progress = { versionSupportTime.intValue.toFloat() / maxSizeVersionBar },
            color = Color(0xFF03A9F4),
            trackColor = MaterialTheme.colorScheme.tertiary,
            strokeCap = StrokeCap.Square,
            drawStopIndicator = {},
            modifier = modifier.height(10.dp)
        )
    }
}

@Composable
fun DisplayFace(currentMood: MutableState<MoodEnum>, modifier: Modifier) {
    val expression = getMoodRepresentation(currentMood.value)
    Box(
        modifier.fillMaxHeight(0.30f)
    ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "background",
            modifier.fillMaxSize()
                .wrapContentSize()
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
        )
        Image(
            painter = expression,
            contentDescription = "Alex's face",
            modifier.fillMaxSize()
        )
    }
}

@Composable
fun DisplayInteractionsMenu(currentMood: MutableState<MoodEnum>,context: Context, health: MutableIntState, hunger: MutableIntState, version: MutableIntState, modifier: Modifier) {
    Row{
        Button(onClick = {
            hunger.intValue--
            val initialMood = currentMood.value
            currentMood.value = Mood.getHappierMood(currentMood.value) //get the happier face
            val handler = Handler(Looper.getMainLooper())
            val runnable = Runnable{
                currentMood.value =initialMood
            }
            handler.postDelayed(runnable,2000)
        },
            shape = RectangleShape,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
            ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.food_apple),
                contentDescription = "apple icon",
                tint = MaterialTheme.colorScheme.tertiary
            )

        }
        Button(onClick = {
            version.intValue--
        },
            shape = RectangleShape,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_terminal_24),
                contentDescription = "terminal icon",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun DisplayActivity(
    mood: MutableState<MoodEnum>,
    context: Context,
    version: MutableIntState,
    shell: MutableState<Boolean>,
    modifier: Modifier
) {
    if(shell.value){
        DisplayShell(mood, context, version, modifier)
    }
    else{
        DisplayGarden(mood, context, version, modifier)
    }
}



@Composable
fun DisplayShell(x0: MutableState<MoodEnum>, x1: Context, x2: MutableIntState, modifier: Modifier) {
    Text(
        text = "terminal"
    )
}

@Composable
fun DisplayGarden(x0: MutableState<MoodEnum>, x1: Context, x2: MutableIntState, modifier: Modifier) {
    Text(
        text = "garden"
    )
}

@Preview
@Composable
fun GreetingsPreview(){
    AlexTheme {
        Greeting()
    }
}