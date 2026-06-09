package com.example.lvluptemplate.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lvluptemplate.model.dao.MusicDao
import com.example.lvluptemplate.model.entities.GenreEntity
import com.example.lvluptemplate.model.entities.PlaylistEntity
import com.example.lvluptemplate.model.entities.PlaylistSongCrossRef
import com.example.lvluptemplate.model.entities.SongEntity

@Database(
    entities = [
        SongEntity::class,
        GenreEntity::class,
        PlaylistEntity::class,
        PlaylistSongCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "music_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }
}
