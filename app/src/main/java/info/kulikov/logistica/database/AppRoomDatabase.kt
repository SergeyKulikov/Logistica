package info.kulikov.logistica.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import info.kulikov.logistica.Somewhat

@Database(entities = [Somewhat::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "test7_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}