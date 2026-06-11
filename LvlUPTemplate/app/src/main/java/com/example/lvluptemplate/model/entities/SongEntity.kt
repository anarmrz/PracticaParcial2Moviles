package com.example.lvluptemplate.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/*
RELACION DE SONG Y GENRE: 1:N

"Queremos almacenar Canciones... de ellas lo más importante será tomar... género" y luego dice
"Para centralizar todo, el género será su propia entidad", te está indicando la jerarquía:

Un Género puede tener muchas canciones (ej. Género "Rock" tiene 100 canciones).

Una Canción tiene asignado un solo género (según la estructura que te piden).*/



@Entity(
    tableName = "songs",
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val coverUrl: String,
    val genreId: String,
    val playCount: Int = 0 //Para la parte de puntos extra

)