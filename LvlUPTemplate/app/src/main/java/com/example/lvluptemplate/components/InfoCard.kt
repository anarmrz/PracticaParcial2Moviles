package com.example.lvluptemplate.components

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.lvluptemplate.viewmodel.MusicViewModel

@Composable
fun InfoCard(
    songId: String,
    songArtist: String,
    songTitle: String,
    songCover: String,
    onSongClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(180.dp, 240.dp)
            .padding(end = 16.dp)
            .background(Color(0xFF1A1A1A), RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable{onSongClick(songId)}

    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF333333))
                    .height(150.dp)
                    .clickable { onSongClick(songId) }
            ){
                AsyncImage(
                    //Cambiar model por las imagenes de las canciones
                    model = songCover,
                    contentDescription = "Cover de portada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = songTitle,
                color = Color.White
            )
            Text(
                text = songArtist,
                color = Color.Gray
            )
        }
        }
    }
