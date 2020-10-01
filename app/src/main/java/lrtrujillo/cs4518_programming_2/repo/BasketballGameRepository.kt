package lrtrujillo.cs4518_programming_2.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import lrtrujillo.cs4518_programming_2.BasketballGame
import lrtrujillo.cs4518_programming_2.database.BasketballGameDao
import lrtrujillo.cs4518_programming_2.database.BasketballGameDatabase
import java.io.File
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME: String = "game-database"

class BasketballGameRepository private constructor(context: Context) {

    private val executor = Executors.newSingleThreadExecutor();

    private val database: BasketballGameDatabase = Room.databaseBuilder(
        context.applicationContext,
        BasketballGameDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    private val basketballGameDao = database.basketballGameDao()

    private val filesDir = context.applicationContext.filesDir

    fun getBasketballGames() : LiveData<List<BasketballGame>>  {
        Log.d("dsd", basketballGameDao.getBasketballGames().value.toString());
        return basketballGameDao.getBasketballGames()
    }
    fun getBasketballGame(id: UUID) : LiveData<BasketballGame?> = basketballGameDao.getBasketballGame(id)

    fun getTeamAWins() : LiveData<List<BasketballGame>> = basketballGameDao.getTeamAWins()
    fun getTeamBWins() : LiveData<List<BasketballGame>> = basketballGameDao.getTeamBWins()

    fun getTeamAPhoto(game: BasketballGame) : File {
        var file = File(filesDir, game.teamAPhotoName);

        if(!file.exists()) { file = File(filesDir, "celtics.png") }

        return file;
    }
    fun getTeamBPhoto(game: BasketballGame) : File {
        var file = File(filesDir, game.teamBPhotoName);

        if(!file.exists()) { file = File(filesDir, "heat.png") }

        return file;
    }

    fun addBasketballGame(game: BasketballGame) {
        Log.d(DATABASE_NAME, "addBasketballGame(${game.id.toString()}")
        executor.execute {
            basketballGameDao.addBasketballGame(game)
        }
    }
    fun updateBasketballGame(game: BasketballGame) {
        executor.execute {
            basketballGameDao.updateBasketballGame(game)
        }
    }

    companion object {
        private var INSTANCE: BasketballGameRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BasketballGameRepository(context)
            }
        }
        fun get(): BasketballGameRepository {
            return INSTANCE ?: throw IllegalStateException("BaskballGameRepository must be initialized!")
        }

    }
}