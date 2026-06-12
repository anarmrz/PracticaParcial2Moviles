package com.example.lvluptemplate.view.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lvluptemplate.components.AddPlaylistCard
import com.example.lvluptemplate.components.CreatePlaylistDialog
import com.example.lvluptemplate.components.MiniPlayerComponent
import com.example.lvluptemplate.components.PlaylistCardComponent
import com.example.lvluptemplate.components.SimpleBottomBar
import com.example.lvluptemplate.viewmodel.MusicViewModel

data class Playlist(val id: String, val name: String, val tracksCount: Int)

@Composable
fun PlaylistsScreen(
    viewModel: MusicViewModel,
    onPlaylistClick: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateMenu: (String) -> Unit
) {
    val allSongs by viewModel.allSongs.collectAsState(initial = emptyList())

    val allPlaylist by viewModel.allPlaylists.collectAsState(initial = emptyList())

    val songsForPlaylist by viewModel.getSongsForPlaylist(allPlaylist[0].id).collectAsState(emptyList())

    val tracksCount = songsForPlaylist.size

    var showDialog by remember { mutableStateOf(false) }


    Scaffold(
        bottomBar = {
            Column {
                // Validación de seguridad obligatoria
                if (allSongs.isNotEmpty()) {
                    val currentSong = allSongs[0] // Tomas la canción

                    MiniPlayerComponent(
                        viewModel = viewModel,
                        songId = currentSong.id,       // Pasas solo el ID
                        title = currentSong.title,     // Pasas solo el título
                        artist = currentSong.artist    // Pasas solo el artista
                    )
                }
                SimpleBottomBar(onNavigateMenu = onNavigateMenu)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
        ) {

            Text(
                text = "Your Playlists",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
            )





            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                item{

                    AddPlaylistCard(onClick = { showDialog = true })

                    if(showDialog){
                        CreatePlaylistDialog(
                            onDismiss = { showDialog = false },
                            onPlaylistCreated = { playlistName ->
                                viewModel.createPlaylist(
                                    name = playlistName,
                                    description = "Custom Playlist"
                                )
                                showDialog = false
                            }
                        )
                    }
                }

                items(allPlaylist) { playlist ->

                    val uiPlaylist = Playlist(
                        id = playlist.id,
                        name = playlist.name,
                        tracksCount = tracksCount
                    )

                    PlaylistCardComponent(
                        playlist = uiPlaylist,
                        onClick = { onPlaylistClick(playlist.id) }
                    )
                }

            }
        }
    }
}





