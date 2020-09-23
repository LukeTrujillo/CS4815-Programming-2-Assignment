package lrtrujillo.cs4518_programming_2.database

import androidx.room.TypeConverter
import java.util.*

class BasketballGameTypeConverters {

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?) : String? {
        return uuid?.toString()
    }
}