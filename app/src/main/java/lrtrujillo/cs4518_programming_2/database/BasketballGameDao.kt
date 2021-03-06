package lrtrujillo.cs4518_programming_2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import lrtrujillo.cs4518_programming_2.BasketballGame
import lrtrujillo.cs4518_programming_2.BasketballGameViewModel
import java.util.*

@Dao
interface BasketballGameDao {

    @Query("SELECT * FROM table_game")
    fun getBasketballGames() : LiveData<List<BasketballGame>>

    @Query("SELECT * FROM table_game WHERE id=(:id)")
    fun getBasketballGame(id: UUID): LiveData<BasketballGame?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBasketballGame(basketballGame: BasketballGame)

    @Update
    fun updateBasketballGame(basketballGame: BasketballGame)

    @Query("SELECT * FROM table_game WHERE teamAScore >= teamBScore")
    fun getTeamAWins() : LiveData<List<BasketballGame>>

    @Query("SELECT * FROM table_game WHERE teamAScore < teamBScore")
    fun getTeamBWins() : LiveData<List<BasketballGame>>



}