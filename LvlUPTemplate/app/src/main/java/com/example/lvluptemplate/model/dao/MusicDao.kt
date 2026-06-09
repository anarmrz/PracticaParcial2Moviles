package com.example.lvluptemplate.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lvluptemplate.components.Song
import com.example.lvluptemplate.model.entities.GenreEntity
import com.example.lvluptemplate.model.entities.PlaylistEntity
import com.example.lvluptemplate.model.entities.PlaylistSongCrossRef
import com.example.lvluptemplate.model.entities.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Insertar cancion (ya viene en el dummydata, no se usara aca)
    suspend fun insertSong(song: SongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Insertar genero (ya viene en el dummydata, no se usara aca)
    suspend fun insertGenre(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Insertar playlist (En el componente createplaylistdialog, tercera parte)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Insertar playlist con sus respectivas canciones (Agregar a favoritos y agregar a custom playlist, quinta parte y puntos extra)
    suspend fun insertPlaylistSongCrossRef(playlistSongCrossRef: PlaylistSongCrossRef)

    @Query("SELECT * FROM songs") //Mostrar canciones (En el mainscreen, primera parte)
    fun getSongs():  Flow<List<SongEntity>>

    @Query("SELECT * FROM genres") //Mostrar genero (no se implementa nunca, asi que no es necesaria)
    fun getGenres():  Flow<List<GenreEntity>>

    @Query("SELECT * FROM playlists") //Mostrar playlist (En playlistscreen, tercera parte)
    fun getPlaylists():  Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM songs WHERE genreId = :genreId") //Mostrar la cancion con su genero (no pide justo eso aparte asi que no es necesario)
    fun getSongsByGenre(genreId: Long):  Flow<List<SongEntity>>

    /*
    * ESTA NO: porque simplemente me devolvera dos numeros, que es el id de la cancion y el id de la playlist entonces no necesito
    * que en la interfaz se vea eso, sino el nombre de la cancion y la playlist y asi por eso mejor hacer la siguiente con inner join
    * @Query("SELECT * FROM playlist_song_cross_ref") //Mostrar playlist con sus respectivas canciones ?????
    fun getPlaylistsSongsCrossRef(): Flow<List<PlaylistSongCrossRef>>
    * */


    //Mostrar playlist con sus respectivas canciones (En PlaylistScreen, Tercera parte)
    @Query("SELECT * FROM songs " +
            "INNER JOIN playlist_song_cross_ref crossRef ON songs.id = crossRef.songId " +
            "WHERE crossRef.playlistId = :playlistId")
    fun getSongsForPlaylist(playlistId: Long): Flow<List<SongEntity>>

    //Mostrar cancion (Al buscarla por su titulo, artista, album o genero en SearchScreen, Segunda parte)
    @Query("SELECT * FROM songs " +
            "INNER JOIN genres g ON songs.genreId = g.id " +
            "WHERE songs.title LIKE '%' || :query || '%' " +
            "OR songs.artist LIKE '%' || :query || '%'" +
            "OR songs.album LIKE '%' || :query || '%'" +
            "OR g.name LIKE '%' || :query || '%'"
    )
    fun searchSongs(query: String): Flow<List<SongEntity>>

    // Para los Puntos Extra: Incrementar el contador de reproducciones
    @Query("UPDATE songs SET playCount = playCount + 1 WHERE id = :songId")
    suspend fun incrementPlayCount(songId: Long)


}