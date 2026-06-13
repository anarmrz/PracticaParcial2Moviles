package com.example.lvluptemplate.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lvluptemplate.components.InfoCard
import com.example.lvluptemplate.components.MiniPlayerComponent
import com.example.lvluptemplate.components.SimpleBottomBar
import com.example.lvluptemplate.viewmodel.MusicViewModel
import androidx.compose.runtime.getValue //PARA EL BY
import androidx.compose.foundation.lazy.items //PARA ITERAR ITEMS

@Composable
fun MainScreen(
    viewModel: MusicViewModel,
    onSongClick: (String) -> Unit,
    onNavigateMenu: (String) -> Unit
) {
    val allSongs by viewModel.allSongs.collectAsState(initial = emptyList())
    //Al poner empty list le estás diciendo: "Mientras la base de datos se digna a traerme
    // las canciones reales, dibuja una lista vacía para que la aplicación no colapse".

    //Observamos el estado reactivo del reproductor global para el BottomBar
    val globalPlayingSong by viewModel.currentPlayingSong.collectAsState()

    val favoriteArtistsSongs = allSongs.take(3)
    val newMusicSongs = allSongs.drop(3).take(3)
    val recommendedSongs = allSongs.drop(6)



    Scaffold(
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
            } }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "LvlUP", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.Star, contentDescription = "Logo", tint = Color.White)
                }
            }


            item {
                Column {
                    Text("From your favorite artists", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        /*Iterar en una lista de canciones*/
                        items(favoriteArtistsSongs) { song ->
                            InfoCard(
                                songId = song.id,
                                songArtist = song.artist,
                                songTitle = song.title,
                                songCover = song.coverUrl,
                                onSongClick = onSongClick
                            )
                        }
                    }
                }
            }


            item {
                Column {
                    Text("New Music", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    /*Iterar en una lista de canciones*/
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(newMusicSongs) { song ->
                            InfoCard(
                                songId = song.id,
                                songArtist = song.artist,
                                songTitle = song.title,
                                songCover = song.coverUrl,
                                onSongClick = onSongClick
                            )
                        }
                    }
                }
            }


            item {
                Column {
                    Text("Recommendations", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        /*Iterar en una lista de canciones*/
                        items(recommendedSongs) { song ->
                            InfoCard(
                                songId = song.id,
                                songArtist = song.artist,
                                songTitle = song.title,
                                songCover = song.coverUrl,
                                onSongClick = onSongClick
                            )
                        }
                    }
                }
            }

        }
    }
}



