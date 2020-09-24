package lrtrujillo.cs4518_programming_2


import android.util.Log
import androidx.lifecycle.ViewModel
import lrtrujillo.cs4518_programming_2.repo.BasketballGameRepository
import java.util.*
import kotlin.random.Random

class BasketballGameListViewModel : ViewModel() {
    private val basketballGameRepository = BasketballGameRepository.get()
    val basketballGameListLiveData = basketballGameRepository.getBasketballGames()

    val basketballGameListTeamAWins = basketballGameRepository.getTeamAWins()
    val basketballGameListTeamBWins = basketballGameRepository.getTeamBWins()

}