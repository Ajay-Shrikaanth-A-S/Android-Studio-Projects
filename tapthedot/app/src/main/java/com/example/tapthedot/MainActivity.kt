package com.example.tapthedot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tapthedot.ui.theme.TapthedotTheme
import kotlinx.coroutines.CoroutineScope
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TapthedotTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(10) }
    var isGameActive by remember { mutableStateOf(false) }
    var dotPosition by remember { mutableStateOf(Pair(0f, 0f)) }

    val scope = rememberCoroutineScope()

    // Timer
    LaunchedEffect(isGameActive) {
        if (isGameActive) {
            timeLeft = 10
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            isGameActive = false
        }
    }

    // Start Game Button
    if (!isGameActive) {
        Button(
            onClick = {
                score = 0
                isGameActive = true
                moveDot(scope) { newPosition ->
                    dotPosition = newPosition
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Start Game")
        }
    }

    // Game Display
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Score and Time display
        Text(text = "Score: $score", style = MaterialTheme.typography.titleLarge)
        Text(text = "Time Left: $timeLeft", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Square Box for the Dot
        Box(
            modifier = Modifier
                .size(200.dp) // Size of the square box
                .background(Color.Gray)
                .align(Alignment.CenterHorizontally)
        ) {
            Dot(dotPosition) {
                score++
                generateNewDotPosition { newPosition ->
                    dotPosition = newPosition // Move the dot to a new position after clicking
                }
            }
        }
    }
}

// Function to move the dot continuously
private fun moveDot(scope: CoroutineScope, updateDotPosition: (Pair<Float, Float>) -> Unit) {
    scope.launch {
        while (true) {
            delay(500) // Move dot every 500 ms
            generateNewDotPosition { newPosition ->
                updateDotPosition(newPosition)
            }
        }
    }
}

// Function to generate a new dot position
private fun generateNewDotPosition(onNewPosition: (Pair<Float, Float>) -> Unit) {
    val x = Random.nextFloat() * 0.75f // 75% of the box width
    val y = Random.nextFloat() * 0.75f // 75% of the box height
    onNewPosition(Pair(x, y))
}

// Dot Composable
@Composable
fun Dot(position: Pair<Float, Float>, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(10.dp) // Size of the dot (very small)
            .background(Color.Blue, shape = MaterialTheme.shapes.small) // Make it circular
            .clickable { onClick() }
            .offset(x = (position.first * 200).dp, y = (position.second * 200).dp) // Centered in the box
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    TapthedotTheme {
        GameScreen()
    }
}
