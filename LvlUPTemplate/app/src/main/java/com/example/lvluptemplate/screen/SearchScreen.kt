package com.example.lvluptemplate.screen

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
import com.example.lvluptemplate.components.MiniPlayerComponent
import com.example.lvluptemplate.components.SimpleBottomBar
import com.example.lvluptemplate.components.SongResultRow


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {

    var searchQuery by remember { mutableStateOf("") }


    Scaffold(
        bottomBar = {
            Column() {
                MiniPlayerComponent()
                SimpleBottomBar()
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
        }
    }

}


