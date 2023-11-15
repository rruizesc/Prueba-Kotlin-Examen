package com.example.prueba_kotlin_examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SumadoraApp()
        }
    }
}

@Composable
fun SumadoraApp() {
    val viewModel: SumadoraViewModel = viewModel()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Input) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (currentScreen) {
                is Screen.Input -> {
                    InputScreen(
                        viewModel = viewModel,
                        onSumResult = {
                            currentScreen = Screen.Result(it)
                        }
                    )
                }
                is Screen.Result -> {
                    val resultScreen = currentScreen as Screen.Result
                    ResultScreen(viewModel, resultScreen.result) {

                        currentScreen = Screen.Input
                    }
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(viewModel: SumadoraViewModel, onSumResult: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = viewModel.numero1,
            onValueChange = { viewModel.numero1 = it },
            label = { Text("Número 1") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = viewModel.numero2,
            onValueChange = { viewModel.numero2 = it },
            label = { Text("Número 2") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                viewModel.sumar()
                onSumResult(viewModel.resultadoActual)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Sumar")
        }
    }
}


@Composable
fun ResultScreen(viewModel: SumadoraViewModel, result: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text("Resultado actual:", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(result, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Resultados anteriores:", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))


        val resultadosAnterioresText = buildString {
            for (resultado in viewModel.resultadosAnteriores) {
                append(resultado)
                append("\n")
            }
        }

        Text(resultadosAnterioresText, style = MaterialTheme.typography.bodySmall)

        // Botón para volver a la pantalla de entrada
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Volver a la entrada")
        }
    }
}


sealed class Screen {
    object Input : Screen()
    data class Result(val result: String) : Screen()
}
