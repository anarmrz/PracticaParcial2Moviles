package com.example.lvluptemplate.model.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


/*RELACION N:N:
* Una Playlist puede tener muchas canciones.

Una Canción puede estar en muchas playlists diferentes.
* */
@Entity(
    tableName = "playlist_song_cross_ref",
    primaryKeys = ["playlistId", "songId"]
)
data class PlaylistSongCrossRef(
    val playlistId: Long,
    val songId: Long
)