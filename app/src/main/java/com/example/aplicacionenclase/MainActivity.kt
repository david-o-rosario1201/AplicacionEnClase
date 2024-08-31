package com.example.aplicacionenclase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacionenclase.ui.theme.AplicacionEnClaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionEnClaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PrioridadScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PrioridadScreen(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        OutlinedTextField(
            label = {
                Text("Descripción")
            },
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            label = {
                Text("Dias Compromiso")
            },
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Guardar"
            )
            Text("Guardar")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AplicacionEnClaseTheme {
        PrioridadScreen("Android")
    }
}