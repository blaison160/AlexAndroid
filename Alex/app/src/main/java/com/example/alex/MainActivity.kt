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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alex.management.HealthFunctions.getHealthRepresentation
import com.example.alex.management.MoodEnum
import com.example.alex.management.MoodFunctions.getMoodRepresentation
import com.example.alex.management.MoodFunctions
import com.example.alex.management.MoodViewModel
import com.example.alex.management.ShellFunctions.checkUserInput
import com.example.alex.management.ShellViewModel
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
fun DisplayMainPage(modifier: Modifier, currentMood: MoodViewModel = viewModel(), shell: ShellViewModel = viewModel()){
    val context = LocalContext.current
    val health = rememberSaveable { mutableIntStateOf(6) }
    val maxVersionSupportTime = 32
    val versionSupportTime = rememberSaveable { mutableIntStateOf(maxVersionSupportTime) }
    val maxFoodBar = 8
    val hunger = rememberSaveable { mutableIntStateOf(maxFoodBar)}
    currentMood.updateCurrentMood(MoodFunctions.getMoodFromHealth(health.intValue))
    val shellEnabled = rememberSaveable { mutableStateOf(false) }
    shell.updateShellLines(listOf(stringResource(R.string.greetings),stringResource(R.string.typeHelp)))
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
        DisplayInteractionsMenu(currentMood, context, shellEnabled, health, hunger, versionSupportTime, modifier)
        Box(
            //modifier.fillMaxHeight(0.6F)
        ) {
            DisplayActivity(
                currentMood,
                context,
                versionSupportTime,
                shellEnabled,
                shell,
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
            contentDescription = "heart",
            modifier
        )
    }
}

@Composable
fun DisplayHungerBar(hunger: MutableIntState, modifier: Modifier) {
    //TODO()
}


@Composable
fun DisplayVersionBar(versionSupportTime: MutableIntState, modifier: Modifier) {
    //TODO()
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
fun DisplayInteractionsMenu(currentMood: MoodViewModel, context: Context, shellEnabled: MutableState<Boolean>, health: MutableIntState, hunger: MutableIntState, version: MutableIntState, modifier: Modifier) {
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
                imageVector = ImageVector.vectorResource(R.drawable.appleicon),
                contentDescription = "apple icon",
                tint = MaterialTheme.colorScheme.tertiary
            )

        }
        var icon = R.drawable.baseline_terminal_24
        if(shellEnabled.value){
            icon = R.drawable.tree_outline
        }
        Button(onClick = {
            shellEnabled.value = !shellEnabled.value
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
    shellEnabled: MutableState<Boolean>,
    shell: ShellViewModel,
    hunger: MutableIntState,
    modifier: Modifier
) {
    if(shellEnabled.value){
        DisplayShell(mood, context, version, shell)
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
    shell: ShellViewModel
) {
    val shellContent = shell.shellLines
    val input = rememberSaveable { mutableStateOf(("")) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            OutlinedTextField(
                input.value,
                onValueChange = { input.value = it },
                singleLine = true,
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
                    runCommand(input.value,shell, mood, context)
                    input.value = ""
                }
            ) {
                Text("OK")
            }
        }
        Box(
            modifier = Modifier
                .border(4.dp, MaterialTheme.colorScheme.secondary)
                .padding(4.dp)
                .background(Color.Black)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                shellContent.forEach { line ->
                    Text(
                        text = line,
                        color = Color.Green
                    )
                }
            }
        }
    }
}

fun runCommand(input: String, shell: ShellViewModel, mood: MoodViewModel, context: Context){
    val transformedInput = input.replace(" ","")
        .replace("'","")
        .replace("é","e")
        .replace("à","a")
        .replace("?","")
        .lowercase()
    val linesToDisplay = mutableListOf("[~]$ $input")
    val result = checkUserInput(transformedInput, mood.getCurrentMood(), context)
    mood.updateCurrentMood(result.second)
    linesToDisplay.addAll(result.first)
    shell.updateShellLines(linesToDisplay)
}

@Composable
fun DisplayGarden(context: Context, hunger: MutableIntState, modifier: Modifier) {
    Text(
        text = "garden"
    )
    //TODO("garden minigame")
}

@Preview
@Composable
fun GreetingsPreview(){
    AlexTheme {
        Greeting()
    }
}