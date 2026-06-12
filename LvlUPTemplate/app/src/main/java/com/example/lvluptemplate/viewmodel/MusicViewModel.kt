package com.example.lvluptemplate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lvluptemplate.model.dao.MusicDao
import com.example.lvluptemplate.model.entities.PlaylistEntity
import com.example.lvluptemplate.model.entities.PlaylistSongCrossRef
import com.example.lvluptemplate.model.entities.SongEntity
import com.example.lvluptemplate.resources.DummyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class MusicViewModel(private val musicDao: MusicDao) : ViewModel(){

    /*Para inyectar datos ya quemados adentro de la base de datos, en este caso los datos de dummy data*/
    init {
        viewModelScope.launch {
            if (musicDao.getGenres().first().isEmpty()) {
                musicDao.insertSong(DummyData.allSongs)
                musicDao.insertGenre(DummyData.genres)
                musicDao.insertPlaylist(DummyData.playlists)
                musicDao.insertPlaylistSongCrossRef(DummyData.playlistSongRelations)
            }
        }
    }
    //SEDUNDA PARTE:
    // Mostrar cancion (Al buscarla por su titulo, artista, album o genero en SearchScreen, Segunda parte)
    fun searchSongs(query: String) = musicDao.searchSongs(query)

    //TERCERA PARTE:
    // Crear en la base de datos ya en la interfaz siendo el usuario interactuando con ella, crear una playlist
    fun createPlaylist(name: String, description: String){
        viewModelScope.launch {
            musicDao.insertPlaylist(
                listOf(
                    PlaylistEntity(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        description = description)
                )
            )
        }
    }
        // Mostrar playlist con sus respectivas canciones (En PlaylistScreen, Tercera parte)
    fun getSongsForPlaylist(playlistId: String) = musicDao.getSongsForPlaylist(playlistId)

    //QUINTA PARTE Y PUNTOS EXTRA:
    // Agregar una cancion a una playlist (agregar a favoritos y agregar a custom playlist)
    fun addSongToPlaylist(playlistId: String, songId: String){
        viewModelScope.launch {
            musicDao.insertPlaylistSongCrossRef(
                listOf(
                    PlaylistSongCrossRef(
                        playlistId = playlistId,
                        songId = songId
                    )
                )
            )
        }
    }

    // --- PUNTOS EXTRA (LvlUP Experience) ---
    // Esta función la llamarás cuando el usuario le dé Play a una canción
    fun playSong(songId: Long) {
        viewModelScope.launch {
            musicDao.incrementPlayCount(songId)
        }
    }

    //PRIMERA PARTE
    // Mostrar canciones (En el mainscreen, primera parte)
    val allSongs = musicDao.getSongs().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    //TERCERA PARTE
    // Mostrar playlists (En playlistscreen, tercera parte)
    val allPlaylists = musicDao.getPlaylists().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // ==========================================================
    // PUNTOS EXTRA: ESTADO DEL REPRODUCTOR
    // ==========================================================

    // 1. Variable que guarda la canción que está sonando actualmente.
    // Inicia en null porque al abrir la app no suena nada.
    private val _currentPlayingSong = MutableStateFlow<SongEntity?>(null)
    val currentPlayingSong = _currentPlayingSong.asStateFlow()

    // 2. Función maestra: Cambia la canción en el reproductor Y suma un Play
    fun setCurrentSong(song: SongEntity) {
        _currentPlayingSong.value = song
        // ¡Cumplimos el requerimiento! Al poner la canción en el reproductor, sumamos +1
        playSong(song.id.toLong()) // Asegúrate de que el ID sea Long según tu BD
    }

    // 3. Lógica para botón Siguiente (SIN PARÁMETROS)
    fun playNextSong() {
        val currentList = allSongs.value // El ViewModel lee su propia lista
        val currentSong = _currentPlayingSong.value ?: return

        val currentIndex = currentList.indexOfFirst { it.id == currentSong.id }

        if (currentIndex != -1 && currentIndex < currentList.size - 1) {
            setCurrentSong(currentList[currentIndex + 1])
        }
    }

    // 4. Lógica para botón Anterior (SIN PARÁMETROS)
    fun playPreviousSong() {
        val currentList = allSongs.value // El ViewModel lee su propia lista
        val currentSong = _currentPlayingSong.value ?: return

        val currentIndex = currentList.indexOfFirst { it.id == currentSong.id }

        if (currentIndex > 0) {
            setCurrentSong(currentList[currentIndex - 1])
        }
    }

    /*
     * FACTORY DEL VIEWMODEL (Fábrica)
     * =====================================================================
     * ¿QUÉ HACE?: Le enseña a Android cómo construir este ViewModel.
     *
     * ¿PARA QUÉ SIRVE?: Por defecto, Android solo sabe crear ViewModels vacíos
     * (sin parámetros). Como el nuestro necesita recibir el "musicDao", usamos
     * esta fábrica para inyectárselo manualmente durante su creación.
     */
    class MusicViewModelFactory(private val musicDao: MusicDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicViewModel(musicDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
