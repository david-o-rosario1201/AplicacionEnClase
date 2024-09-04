package com.example.aplicacionenclase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ){
                        PrioridadScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun PrioridadScreen(){
        var descripcion by remember { mutableStateOf("") }
        var diasCompromiso by remember { mutableStateOf("") }
        var errorMessage: String? by remember { mutableStateOf(null) }
        val scope = rememberCoroutineScope()

        val change : (String) -> Unit = { it ->
            diasCompromiso = it
        }

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
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("Descripción")
                            },
                            value = descripcion,
                            onValueChange = {
                                descripcion = it
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = {
                                Text("Dias Compromiso")
                            },
                            value = diasCompromiso,
                            onValueChange = change,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        errorMessage?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                if(descripcion.isBlank())
                                    errorMessage = "La descripción no puede estar vacía"
                                else if(diasCompromiso.toInt() <= 0)
                                    errorMessage = "Días compromiso no puede ser menor que 1"
                                else{
                                    scope.launch {
                                        savePrioridad(
                                            PrioridadEntitity(
                                                descripcion = descripcion,
                                                diasCompromiso = diasCompromiso.toInt()
                                            )
                                        )
                                        descripcion = ""
                                        diasCompromiso = ""
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Guardar"
                            )
                            Text(text = "Guardar")
                        }
                    }
                }
                val lifecycleOwner = LocalLifecycleOwner.current
                val prioridadList by prioridadDb.prioridadDao().getAll()
                    .collectAsStateWithLifecycle(
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED,
                        initialValue = emptyList()
                    )
                PrioridadListScreen(prioridadList = prioridadList)
            }
        }
    }

    @Composable
    fun PrioridadListScreen(
        prioridadList: List<PrioridadEntitity>
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Lista de Prioridades")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(prioridadList){
                    PrioridadRow(it = it)
                }
            }
        }
    }

    @Composable
    fun PrioridadRow(it: PrioridadEntitity){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = it.prioridadId.toString(),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = it.descripcion,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = it.diasCompromiso.toString(),
                modifier = Modifier.weight(2f)
            )
        }
        HorizontalDivider()
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