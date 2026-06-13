package com.example.lvluptemplate.view.screen

import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import com.example.lvluptemplate.components.TrackRowItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import coil.compose.AsyncImage
import com.example.lvluptemplate.components.MiniPlayerComponent
import com.example.lvluptemplate.components.SimpleBottomBar
import com.example.lvluptemplate.viewmodel.MusicViewModel
import kotlin.collections.listOf

data class SongP(val nombre: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun MyPlaylistScreen(
    viewModel: MusicViewModel,
    playlistId: String,
    onSongClick: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateMenu: (String) -> Unit
) {

    //Observamos el estado reactivo del reproductor global para el BottomBar
    val globalPlayingSong by viewModel.currentPlayingSong.collectAsState()

    val playlist by viewModel.allPlaylists.collectAsState(initial = emptyList())

    // 1. Buscamos la playlist específica usando el ID
    val currentPlaylist = playlist.find { it.id == playlistId }

    // 2. Extraemos el nombre (y si por alguna razón no la encuentra, ponemos un texto por defecto)
    val playlistName = currentPlaylist?.name ?: "Unknown Playlist"

    val songsForPlaylist by viewModel.getSongsForPlaylist(playlistId).collectAsState(emptyList())

    // 3. Para la imagen de la playlist, tomamos la imagen de la primera canción (si existe)
    val playlistCover = songsForPlaylist.firstOrNull()?.coverUrl ?: "https://static.vecteezy.com/..." // Pon tu link por defecto aquí

    val topBackgroundColor = Color(0xFF1A1A1A)
    val bottomBackgroundColor = Color(0xFF0D0E11)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {onNavigateBack()}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = topBackgroundColor)
            )
        },
        bottomBar = {
            Column() {
                //Usamos el reproductor global dinámico en lugar de allSongs[0]
                if (globalPlayingSong != null) {
                    MiniPlayerComponent(
                        viewModel = viewModel,
                        songId = globalPlayingSong!!.id,
                        title = globalPlayingSong!!.title,
                        artist = globalPlayingSong!!.artist
                    )
                }
                SimpleBottomBar(onNavigateMenu = onNavigateMenu)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bottomBackgroundColor)
                .padding(paddingValues)
        ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(topBackgroundColor)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray)
                    ) {
                        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF5E5A44))){
                            AsyncImage(
                                //Cambiar model por las imagenes de las canciones
                                model = playlistCover,
                                contentDescription = "Cover de portada",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.matchParentSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = playlistName,
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(25.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {

                    Column(Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(topBackgroundColor))
                        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(bottomBackgroundColor))
                    }

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Button(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(Color(0xFF7E49C3)),
                            shape = RoundedCornerShape(50.dp),
                            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
                            modifier = Modifier.height(50.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                                Text("REPRODUCIR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }

                    }
                }

                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                ) {
                    // Iteración nativa y optimizada de Jetpack Compose
                    items(songsForPlaylist) { song ->
                        TrackRowItem(
                            title = song.title,
                            cover = song.coverUrl,
                            onClick = { onSongClick(song.id) }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

        }
    }
}

