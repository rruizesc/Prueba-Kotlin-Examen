package com.example.prueba_kotlin_examen

// SumadoraViewModel.kt
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SumadoraViewModel : ViewModel() {
    // Estado para los números de las cajas de texto
    var numero1 by mutableStateOf("")
    var numero2 by mutableStateOf("")

    // Estado para el resultado de la operación actual
    var resultadoActual by mutableStateOf("")

    // Lista para almacenar resultados anteriores
    var resultadosAnteriores = mutableListOf<String>()

    // Función para realizar la suma
    fun sumar() {
        val num1 = numero1.toIntOrNull() ?: 0
        val num2 = numero2.toIntOrNull() ?: 0

        val resultado = num1 + num2

        // Actualizar resultado actual
        resultadoActual = "La suma es: $resultado"

        // Guardar resultado en la lista de resultados anteriores
        resultadosAnteriores.add("$num1 + $num2 = $resultado")

        // Limpiar los campos de entrada
        numero1 = ""
        numero2 = ""
    }
}

