package info.kulikov.logistica

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Somewhat(
    @PrimaryKey
    var id: Int,
    var value: Double,
    var image: String
)
