package info.kulikov.logistica.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.kulikov.logistica.Somewhat
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface AppDao {
    @Query("SELECT * FROM `Somewhat`")
    fun loadSomewhatList(): Observable<List<Somewhat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSomewhatList(users: List<Somewhat>): Maybe<List<Long>>
}