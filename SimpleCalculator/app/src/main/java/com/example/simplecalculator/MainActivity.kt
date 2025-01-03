package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        TextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Enter the number 1") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Enter the number 2") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { result = performOperation(number1, number2, "+") }) {
                Text("ADD")
            }
            Button(onClick = { result = performOperation(number1, number2, "-") }) {
                Text("SUB")
            }
            Button(onClick = { result = performOperation(number1, number2, "*") }) {
                Text("MUL")
            }
            Button(onClick = { result = performOperation(number1, number2, "/") }) {
                Text("DIV")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result ?: "",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

fun performOperation(num1: String, num2: String, operation: String): String {
    val number1 = num1.toDoubleOrNull()
    val number2 = num2.toDoubleOrNull()

    if (number1 == null || number2 == null) {
        return "Invalid input"
    }

    return when (operation) {
        "+" -> (number1 + number2).toString()
        "-" -> (number1 - number2).toString()
        "*" -> (number1 * number2).toString()
        "/" -> {
            if (number2 == 0.0) "Infinity"
            else (number1 / number2).toString()
        }
        else -> "Unknown operation"
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    SimpleCalculatorTheme {
        CalculatorApp()
    }
}
