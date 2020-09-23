package lrtrujillo.cs4518_programming_2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import lrtrujillo.cs4518_programming_2.BasketballGame
import lrtrujillo.cs4518_programming_2.BasketballGameViewModel
import java.util.*

@Dao
interface BasketballGameDao {

    @Query("SELECT * FROM basketballgame")
    fun getBasketballGames() : LiveData<List<BasketballGame>>

    @Query("SELECT * FROM basketballgame WHERE id=(:id)")
    fun getBasketballGame(id: UUID): LiveData<BasketballGame?>

    @Query("INSERT INTO basketballgame (id, teamAName, teamBName, teamAScore, teamBScore, timestamp) VALUES (:id, :teamAName, :teamBName, :teamAScore, :teamBScore, :timestamp)")
    fun setBasketballGame(id: UUID, teamAName: String, teamBName: String, teamAScore: Int, teamBScore: Int, timestamp: Long)

    @Query("SELECT * FROM basketballgame WHERE teamAScore >= teamBScore")
    fun getTeamAWins() : LiveData<List<BasketballGame>>

    @Query("SELECT * FROM basketballgame WHERE teamAScore < teamBScore")
    fun getTeamBWins() : LiveData<List<BasketballGame>>

}