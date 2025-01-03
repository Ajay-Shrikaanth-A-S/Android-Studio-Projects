package com.example.dark_light

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Theme.DarkLightTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var themeIndex by remember { mutableStateOf(0) } // Manage theme index

            DarkLightTheme(themeIndex = themeIndex) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Timer App with Theme Toggle
                        TimerApp(
                            onButtonClick = { message ->
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                            },
                            onThemeToggle = {
                                themeIndex = (themeIndex + 1) % 3 // Toggle between themes
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimerApp(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit,
    onThemeToggle: () -> Unit
) {
    var timeElapsed by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var startTime by remember { mutableStateOf(0L) }
    val laps = remember { mutableStateListOf<String>() }
    val decimalFormat = remember { DecimalFormat("0.00") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Timer",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        Text(text = "Time Elapsed: ${decimalFormat.format(timeElapsed / 1000.0)} seconds")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                if (!isRunning) {
                    isRunning = true
                    startTime = System.currentTimeMillis() - timeElapsed
                    scope.launch {
                        while (isRunning) {
                            timeElapsed = System.currentTimeMillis() - startTime
                            delay(100L)
                        }
                    }
                    onButtonClick("Timer started")
                }
            }, enabled = !isRunning) {
                Text(text = "Start")
            }

            Button(onClick = {
                if (isRunning) {
                    isRunning = false
                }
                val lapTime = timeElapsed / 1000.0
                laps.add("Lap ${laps.size + 1}: ${decimalFormat.format(lapTime)} seconds")
                onButtonClick("Laps recorded")
            }) {
                Text(text = "Stop")
            }

            Button(onClick = {
                isRunning = false
                timeElapsed = 0L
                laps.clear()
                onButtonClick("Timer reset")
            }) {
                Text(text = "Reset")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to toggle theme
        Button(onClick = onThemeToggle) {
            Text(text = "Theme✨")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (laps.isNotEmpty()) {
            Text(text = "Laps:")
            laps.forEach { lap ->
                Text(text = lap)
            }
        }
    }
}
