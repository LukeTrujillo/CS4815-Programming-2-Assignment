package lrtrujillo.cs4518_programming_2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import lrtrujillo.cs4518_programming_2.repo.BasketballGameRepository
import java.io.File
import java.sql.Timestamp
import java.util.*

class BasketballGameViewModel : ViewModel() {

    private val basketballGameRepository = BasketballGameRepository.get()

    var uuid: UUID = UUID.randomUUID()

    var teamAScore: Int = 0
    var teamBScore: Int = 0
    var teamAName: String = "Team A";
    var teamBName: String = "Team B";

    var date: Long = System.currentTimeMillis();

    private val gameIDLiveData = MutableLiveData<UUID>()


    var gameLiveData: LiveData<BasketballGame?> =
        Transformations.switchMap(gameIDLiveData) { uuid ->
            basketballGameRepository.getBasketballGame(uuid);
        }

    fun loadGame(id: UUID) {
        gameIDLiveData.value = id
    }

    fun saveGame(game: BasketballGame) {
            basketballGameRepository.addBasketballGame(game)
    }
    fun getTeamAPhotoFile(game: BasketballGame): File { return basketballGameRepository.getTeamAPhoto(game)}
    fun getTeamBPhotoFile(game: BasketballGame): File { return basketballGameRepository.getTeamBPhoto(game)}

    override fun onCleared() {
        super.onCleared();

        Log.d("BasketballGameViewModel", "onCleared() called")
    }

}

@Entity(tableName = "table_game")
data class BasketballGame(@PrimaryKey val id: UUID = UUID.randomUUID(), var teamAName: String = "Team A", var teamBName: String = "Team B", var teamAScore:Int = 0, var teamBScore:Int = 0, var date: Long = System.currentTimeMillis()) {
    val teamAPhotoName get() = "IMG_${id}_A.jpg";
    val teamBPhotoName get() = "IMG_${id}_B.jpg"
}