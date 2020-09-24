package lrtrujillo.cs4518_programming_2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*

class BasketballGameViewModel : ViewModel() {

    var uuid: UUID = UUID.randomUUID()

    var teamAScore: Int = 0
    var teamBScore: Int = 0
    var teamAName: String = "Team A";
    var teamBName: String = "Team B";

    var timestamp: Long = System.currentTimeMillis();



    override fun onCleared() {
        super.onCleared();

        Log.d("BasketballGameViewModel", "onCleared() called")
    }

    fun addScoreTeamA(amt: Int) {
        teamAScore  += amt

        Log.d("BasketballGameViewModel", "addScoreTeamA(" + amt + ") called  -- score changed from " + (teamAScore - amt) + " to " + teamAScore)

    }
    fun addScoreTeamB(amt: Int) {
        teamBScore += amt
        Log.d("BasketballGameViewModel", "addScoreTeamB(" + amt + ") called  -- score changed from " + (teamBScore - amt) + " to " + teamBScore)
    }

    fun getScoreTeamA() : Int {
        Log.d("BasketballGameViewModel", "getScoreTeamA() called -- Team A has a score of " + teamAScore)
        return teamAScore
    }
    fun getScoreTeamB() : Int {
        Log.d("BasketballGameViewModel", "getScoreTeamB() called -- Team B has a score of " + teamBScore)
        return teamBScore
    }

    fun reset() {
        teamAScore = 0; teamBScore = 0;

        Log.d("BasketballGameViewModel", "reset() called -- Team A & Team B both have a score of 0");
    }
}

@Entity(tableName = "table_game")
data class BasketballGame(@PrimaryKey val id: UUID = UUID.randomUUID(), var teamAName: String = "Team A", var teamBName: String = "Team B", var teamAScore:Int = 0, var teamBScore:Int = 0, var date: Long = System.currentTimeMillis())