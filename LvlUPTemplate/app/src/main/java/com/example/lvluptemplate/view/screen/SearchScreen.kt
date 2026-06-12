package com.example.lvluptemplate.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lvluptemplate.components.MiniPlayerComponent
import com.example.lvluptemplate.components.SimpleBottomBar
import com.example.lvluptemplate.components.Song
import com.example.lvluptemplate.components.SongResultRow
import com.example.lvluptemplate.viewmodel.MusicViewModel


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MusicViewModel,
    onSongClick: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateMenu: (String) -> Unit
) {

    val allSongs by viewModel.allSongs.collectAsState(initial = emptyList())

    var searchQuery by remember { mutableStateOf("") }

    val searchResults by viewModel.searchSongs(searchQuery).collectAsState(initial = emptyList())



    Scaffold(
        bottomBar = {
            Column() {
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
            } }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Search",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            //BARRA DE NAVEGACIÓN
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                placeholder = { Text("Artists or songs...", color = Color.Gray) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF151515),
                    unfocusedContainerColor = Color(0xFF151515),
                    focusedBorderColor = Color(0xFF7E49C3),
                    unfocusedBorderColor = Color(0xFF2C2C2C),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(searchResults) { song ->
                    SongResultRow(
                        song = Song(
                            song.title,
                            song.artist,
                            song.coverUrl
                        ),
                        onClick = { onSongClick(song.id) }
                    )
                }


            }
        }
    }

}


