package com.example.lvluptemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lvluptemplate.view.screen.MainScreen
import com.example.lvluptemplate.view.ui.theme.LvlUPTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LvlUPTemplateTheme {
                MainScreen()
            }
        }
    }
}
