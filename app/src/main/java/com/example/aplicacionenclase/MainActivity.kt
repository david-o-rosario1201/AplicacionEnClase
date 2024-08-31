package com.example.aplicacionenclase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.aplicacionenclase.data.local.database.PrioridadDb
import com.example.aplicacionenclase.data.local.entities.PrioridadEntitity
import com.example.aplicacionenclase.ui.theme.AplicacionEnClaseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "PrioridadDb"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            AplicacionEnClaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PrioridadScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun PrioridadScreen(
        modifier: Modifier = Modifier
    ){
        var descripcion by remember { mutableStateOf("") }
        var diasCompromiso by remember { mutableStateOf(0) }
        var errorMessage: String? by remember { mutableStateOf(null) }
        val scope = rememberCoroutineScope()

        Scaffold{ innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ){
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
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
                        Spacer(modifier = Modifier.padding(10.dp))
                        errorMessage?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }

                        Row {
                            OutlinedButton(
                                onClick = {
                                    descripcion = ""
                                    diasCompromiso = 0
                                    errorMessage = null
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Nuevo"
                                )
                                Text("Nuevo")
                            }

                            OutlinedButton(
                                onClick = {
                                    if(descripcion.isBlank())
                                        errorMessage = "La descripción no puede estar vacía"
                                    else if(diasCompromiso <= 0)
                                        errorMessage = "Días compromiso no puede ser menor que 1"
                                    else{
                                        scope.launch {

                                        }
                                    }
                                }
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun savePrioridad(prioridad: PrioridadEntitity){
        prioridadDb.prioridadDao().save(prioridad)
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        AplicacionEnClaseTheme {
            PrioridadScreen()
        }
    }
}