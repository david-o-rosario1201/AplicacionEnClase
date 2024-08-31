package com.example.aplicacionenclase.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.aplicacionenclase.data.local.entities.PrioridadEntitity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao {
    @Upsert()
    suspend fun save(prioridad: PrioridadEntitity)

    @Query(
        """
            SELECT * FROM Prioridades
            WHERE prioridadId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): PrioridadEntitity?

    @Delete
    suspend fun delete(prioridad: PrioridadEntitity)

    @Query("SELECT * FROM Prioridades")
    fun getAll(): Flow<List<PrioridadEntitity>>
}