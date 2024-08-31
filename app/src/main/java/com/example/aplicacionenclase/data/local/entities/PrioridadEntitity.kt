package com.example.aplicacionenclase.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prioridades")
data class PrioridadEntitity(
    @PrimaryKey
    val prioridadId: Int? = null,
    val descripcion: String = "",
    val asunto: String = ""
)
