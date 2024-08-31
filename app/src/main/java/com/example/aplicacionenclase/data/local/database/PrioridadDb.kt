package com.example.aplicacionenclase.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aplicacionenclase.data.local.dao.PrioridadDao
import com.example.aplicacionenclase.data.local.entities.PrioridadEntitity

@Database(
    version = 1,
    exportSchema = false,
    entities = [PrioridadEntitity::class]
)
abstract class PrioridadDb: RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
}