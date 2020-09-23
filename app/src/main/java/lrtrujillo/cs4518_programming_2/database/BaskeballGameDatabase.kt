package lrtrujillo.cs4518_programming_2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import lrtrujillo.cs4518_programming_2.BasketballGame

@Database(entities = [BasketballGame::class], version = 1)
@TypeConverters(BasketballGameTypeConverters::class)
abstract class BasketballGameDatabase : RoomDatabase() {
    abstract fun basketballGameDao() : BasketballGameDao
}