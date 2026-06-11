package com.example.lvluptemplate.view.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lvluptemplate.components.MiniPlayerComponent
import com.example.lvluptemplate.components.SimpleBottomBar
import com.example.lvluptemplate.components.TrackRowItem
import com.example.lvluptemplate.viewmodel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun SongDetailScreen(
    viewModel: MusicViewModel,
    songId: String,
    onNavigateBack: () -> Unit,
    navController: NavHostController
) {
    val topBackgroundColor = Color(0xFF1A1A1A)
    val bottomBackgroundColor = Color(0xFF0D0E11)
    val darkCardColor = Color(0xFF161920)
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { /* Volver atrás */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = topBackgroundColor)
            )
        },
        bottomBar = {
                Column() {
                    MiniPlayerComponent()
                    SimpleBottomBar()
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
                                model = "https://cdn-images.dzcdn.net/images/cover/5718f7c81c27e0b2417e2a4c45224f8a/0x1900-000000-80-0-0.jpg",
                                contentDescription = "Cover de portada",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.matchParentSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //TITULO DE LA CANCION
                    Text(
                        text = "Song name",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    //ARTISTA
                    Text(
                        text = "By Artist",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
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
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(darkCardColor)
                                .clickable {/* Añadir cancion a PLAYLIST*/ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Gray, modifier = Modifier.size(20.dp))
                        }

                        Spacer(modifier = Modifier.width(16.dp))


                        Button(
                            onClick = { Toast.makeText(context,"Reproduciendo",Toast.LENGTH_SHORT).show() },
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

                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(darkCardColor)
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = Color.Gray, modifier = Modifier.size(20.dp))
                        }
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                ) {

                    Spacer(modifier = Modifier.height(24.dp))
                    TrackRowItem(title = "Song title")
                }

        }
    }
}

