package com.example.lvluptemplate.model.entities

import androidx.room.Entity


/*RELACION N:N:
* Una Playlist puede tener muchas canciones.

Una Canción puede estar en muchas playlists diferentes.
* */
@Entity(
    tableName = "playlist_song_cross_ref",
    primaryKeys = ["playlistId", "songId"]
)
data class PlaylistSongCrossRef(
    val playlistId: String,
    val songId: String
)