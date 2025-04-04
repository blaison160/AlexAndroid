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
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alex.management.Health.getHealthRepresentation
import com.example.alex.management.MoodEnum
import com.example.alex.management.MoodFunctions.getMoodRepresentation
import com.example.alex.management.MoodFunctions
import com.example.alex.management.MoodViewModel
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
fun DisplayMainPage(modifier: Modifier, currentMood: MoodViewModel = viewModel()){
    val context = LocalContext.current
    val health = rememberSaveable { mutableIntStateOf(6) }
    val maxSizeVersionBar = 30
    val versionSupportTime = rememberSaveable { mutableIntStateOf(maxSizeVersionBar) }
    val maxSizeHungerBar = 10
    val hunger = rememberSaveable { mutableIntStateOf(maxSizeHungerBar)}
    currentMood.updateCurrentMood(MoodFunctions.getMoodFromHealth(health.intValue))
    val shell = rememberSaveable { mutableStateOf(false) }
    val shellStrings = remember { mutableStateListOf(String()) }
    shellStrings.remove("")
    shellStrings.add(stringResource(R.string.greetings))
    shellStrings.add(stringResource(R.string.typeHelp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box{
                DisplayFace(currentMood, modifier)
            }
            Column {
                DisplayHeart(health, modifier)

            }
        }
        DisplayInteractionsMenu(currentMood, context, shell, health, hunger, versionSupportTime, modifier)
        Box(
            //modifier.fillMaxHeight(0.6F)
        ) {
            DisplayActivity(
                currentMood,
                context,
                versionSupportTime,
                shell,
                shellStrings,
                hunger,
                modifier
            )
        }
    }
}



@Composable
fun DisplayHeart(health: MutableIntState, modifier: Modifier) {
    val heart = getHealthRepresentation(health.intValue)
    Box{
        Image(
            painter = heart,
            contentDescription = "heart"
        )
    }
}

@Composable
fun DisplayHungerBar(maxSizeHungerBar: Int, hunger: MutableIntState, modifier: Modifier) {

        LinearProgressIndicator(
            progress = { hunger.intValue.toFloat() / maxSizeHungerBar },
            color = Color(0xFFFF9800),
            trackColor = MaterialTheme.colorScheme.tertiary,
            strokeCap = StrokeCap.Square,
            drawStopIndicator = {},
            modifier = modifier.height(15.dp)
        )
    }


@Composable
fun DisplayVersionBar(maxSizeVersionBar: Int, versionSupportTime: MutableIntState, modifier: Modifier) {
    LinearProgressIndicator(
        progress = { versionSupportTime.intValue.toFloat() / maxSizeVersionBar },
        color = Color(0xFF03A9F4),
        trackColor = MaterialTheme.colorScheme.tertiary,
        strokeCap = StrokeCap.Square,
        drawStopIndicator = {},
        modifier = modifier.height(15.dp)
            .padding(2.dp)
    )
}

@Composable
fun DisplayFace(currentMood: MoodViewModel, modifier: Modifier) {
    val expression = getMoodRepresentation(currentMood.getCurrentMood())
    Box{
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "background",
            modifier.padding(4.dp)
                .wrapContentSize()
                .border(BorderStroke(4.dp, MaterialTheme.colorScheme.secondary))
        )
        Image(
            painter = expression,
            contentDescription = "Alex's face",
            modifier.padding(4.dp)
        )
    }
}

@Composable
fun DisplayInteractionsMenu(currentMood: MoodViewModel, context: Context, shell: MutableState<Boolean>, health: MutableIntState, hunger: MutableIntState, version: MutableIntState, modifier: Modifier) {
    val feedEnabled = rememberSaveable { mutableStateOf(true) }//todo : check for food & change icon
    Row{
        Button(
            enabled = feedEnabled.value,
            onClick = {
                feedEnabled.value = false
                val initialMood = currentMood.getCurrentMood()
                currentMood.updateCurrentMood(MoodFunctions.getHappierMood(initialMood)) //get the happier face
                val handler = Handler(Looper.getMainLooper())
                val runnable = Runnable{
                    currentMood.updateCurrentMood(initialMood)
                    feedEnabled.value=true
                }
                handler.postDelayed(runnable,2000)
            },
            shape = RectangleShape,
            border = BorderStroke(4.dp, MaterialTheme.colorScheme.secondary)
            ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.food_apple),
                contentDescription = "apple icon",
                tint = MaterialTheme.colorScheme.tertiary
            )

        }
        var icon = R.drawable.baseline_terminal_24
        if(shell.value){
            icon = R.drawable.tree_outline
        }
        Button(onClick = {
            shell.value = !shell.value
        },
            shape = RectangleShape,
            border = BorderStroke(4.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = "terminal icon",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun DisplayActivity(
    mood: MoodViewModel,
    context: Context,
    version: MutableIntState,
    shell: MutableState<Boolean>,
    shellContent: MutableList<String>,
    hunger: MutableIntState,
    modifier: Modifier
) {
    if(shell.value){
        DisplayShell(mood, context, version, shellContent)
    }
    else{
        DisplayGarden(context, hunger, modifier)
    }
}



@Composable
fun DisplayShell(
    mood: MoodViewModel,
    context: Context,
    version: MutableIntState,
    shellContent: MutableList<String>
) {
    val textList = shellContent
    var input by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .border(4.dp, MaterialTheme.colorScheme.secondary)
                .padding(4.dp)
                .background(Color.Black)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                textList.forEach { text ->
                    Text(
                        text = text,
                        color = Color.Green
                    )
                }
            }
        }
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },//todo : update list with command+result
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Green,
                unfocusedTextColor = Color.Green,
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )
        Button(
            onClick = {
                val transformedText = input.text.lowercase().replace(" ","")
                println(transformedText)
                input = TextFieldValue("")
            }
        ) {
            Text("OK")
        }
    }
}

@Composable
fun DisplayGarden(context: Context, hunger: MutableIntState, modifier: Modifier) {
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