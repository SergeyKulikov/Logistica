package info.kulikov.logistica

import android.app.Application
import info.kulikov.logistica.database.AppRoomDatabase

class App : Application() {
   val database by lazy { AppRoomDatabase.getDatabase(this) }
   val dao by lazy { database.appDao() }
}
