package com.example.lvluptemplate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lvluptemplate.viewmodel.MusicViewModel
import com.example.lvluptemplate.view.screen.MainScreen
import com.example.lvluptemplate.view.screen.MyPlaylistScreen
import com.example.lvluptemplate.view.screen.SongDetailScreen
import com.example.lvluptemplate.view.screen.PlaylistsScreen
import com.example.lvluptemplate.view.screen.SearchScreen


@Composable
fun NavGraph(navController: NavHostController, viewModel: MusicViewModel){

    NavHost(navController = navController, startDestination = "main_screen"){

        composable("main_screen") {
            MainScreen(
                viewModel = viewModel,
                onSongClick = { songId -> navController.navigate("song_screen/$songId") },
                navController = navController
            )
                //Parte 1: que al pulsar la card me dirija a la pantalla de la cancion
                /* 'songId ->'
                * - Concepto: Es una función Lambda (una acción pasada como variable).
                * - La parte izquierda (songId): Es el dato que la pantalla te está "gritando"
                * cuando el usuario hace clic. (Ej. "¡Ey, el usuario tocó el ID 5!").
                * - La flecha (->): Significa la orden de ejecución: "Toma este dato y haz lo siguiente...".
                * - La parte derecha (navController.navigate): Es la acción exclusiva del NavGraph
                * donde usa ese dato para hacer el viaje.*/
        }

        composable("my_playlist_screen/{playlistId}") { backStackEntry ->                  /*'backStackEntry' : Es la "Maleta de Viaje" o el "Ticket de vuelo" de la ruta actual.
                                                                                                  * Cuando el navController viaja a "song_screen/5", genera este
                                                                                                  * objeto backStackEntry. Aquí adentro viene guardado todo el contexto de ese
                                                                                                  * viaje específico, incluyendo la ruta exacta, el ciclo de vida y los
                                                                                                  * parámetros ocultos que mandó la pantalla anterior.
                                                                                                  */

            val safePlaylistId = backStackEntry.arguments?.getString("playlistId") ?: "0"  /*Si por algún error técnico lo anterior viene Nulo, NO explotes la aplicación.
                                                                                                 * Simplemente asigna un '0' como valor de emergencia
                                                                                                 */

            MyPlaylistScreen(
                viewModel = viewModel,
                playlistId = safePlaylistId,
                onSongClick = { songId -> navController.navigate("song_screen/$songId") },
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable("playlist_screen") {
            PlaylistsScreen(
                viewModel = viewModel,
                onPlaylistClick = { playlistId -> navController.navigate("my_playlist_screen/$playlistId") },
                onNavigateBack = { navController.popBackStack() },
                navController = navController
                )
        }

        composable ("search_screen"){
            SearchScreen(
                viewModel = viewModel,
                onSongClick = { songId -> navController.navigate("song_screen/$songId") },
                onNavigateBack = { navController.popBackStack() },
                navController = navController
                )
        }

        composable("song_screen/{songId}"){backStackEntry ->
            val safeSongId = backStackEntry.arguments?.getString("songId") ?: "0"

            SongDetailScreen(
                viewModel = viewModel,
                songId = safeSongId,
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

    }

}


