package com.example.lvluptemplate.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun SimpleBottomBar() {
    var selectedIndex by remember { mutableStateOf(0) }
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.List
    )

    NavigationBar(
        containerColor = Color(0xFF0F0F0F)
    ) {
        icons.forEachIndexed { index, icon ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { selectedIndex = index },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    indicatorColor = Color(0xFF7E49C3),
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}