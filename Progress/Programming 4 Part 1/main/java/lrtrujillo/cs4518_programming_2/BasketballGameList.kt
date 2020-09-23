package lrtrujillo.cs4518_programming_2


import android.util.Log
import androidx.lifecycle.ViewModel

class BasketballGameListViewModel : ViewModel() {

    lateinit var gameList : ArrayList<BasketballGameViewModel>

      init {
            gameList = ArrayList<BasketballGameViewModel>()

            for(i in 1..100) {
                var newGame = BasketballGameViewModel()
                newGame.teamBScore = (1..100).shuffled().first();
                newGame.teamAScore = (1..100).shuffled().first();

                newGame.teamAName =  java.util.UUID.randomUUID().toString().substring(0, 5);
                newGame.teamBName =  java.util.UUID.randomUUID().toString().substring(0, 5);

                newGame.timestamp = newGame.timestamp / (1..9).shuffled().first()
                gameList.add(newGame);
            }
      }
}