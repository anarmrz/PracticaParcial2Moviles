package com.example.lvluptemplate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CreatePlaylistDialog(
    onDismiss: () -> Unit,
    onPlaylistCreated: (String) -> Unit
){
    var title by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Nueva Playlist",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White,focusedBorderColor = Color(0xFF7E49C3)),
                modifier = Modifier.fillMaxWidth(),

                )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
            ) {
                Button(onClick = { onDismiss()},colors = ButtonDefaults.buttonColors(Color(0xFF7E49C3))) {
                    Text(text = "Cerrar")
                }

                Button(onClick = {
                    if (title.isNotBlank()){
                        onPlaylistCreated(title)
                    }
                },colors = ButtonDefaults.buttonColors(Color(0xFF7E49C3)),
                    enabled = title.isNotBlank()) {
                    Text("Crear")
                }
            }
        }
    }
}