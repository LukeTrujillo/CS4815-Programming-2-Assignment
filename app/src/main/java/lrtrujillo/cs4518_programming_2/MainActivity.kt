package lrtrujillo.cs4518_programming_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

private const val SCORE_A = "score_a"
private const val SCORE_B = "score_b"

class MainActivity : AppCompatActivity() {

    private lateinit var teamAThreePointButton: Button
    private lateinit var teamATwoPointButton: Button
    private lateinit var teamAFreethrowButton: Button

    private lateinit var teamBThreePointButton: Button
    private lateinit var teamBTwoPointButton: Button
    private lateinit var teamBFreethrowButton: Button

    private lateinit var resetButton: Button

    private lateinit var teamAScore: TextView
    private lateinit var teamBScore: TextView

    private val game: BasketballGameViewModel by lazy {
        ViewModelProviders.of(this).get(BasketballGameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate(savedInstanceState: Bundle?) called");

        setContentView(R.layout.activity_main)

        teamAThreePointButton = findViewById(R.id.teamAThreePointBtn)
        teamATwoPointButton = findViewById(R.id.teamATwoPointBtn)
        teamAFreethrowButton = findViewById(R.id.teamAFreeThrowBtn)

        teamBThreePointButton = findViewById(R.id.teamBThreePointBtn)
        teamBTwoPointButton = findViewById(R.id.teamBTwoPointBtn)
        teamBFreethrowButton = findViewById(R.id.teamBFreeThrowBtn)

        resetButton = findViewById(R.id.resetButton)

        val currentScoreA = savedInstanceState?.getInt(SCORE_A, 0) ?: 0
        val currentScoreB = savedInstanceState?.getInt(SCORE_B, 0) ?: 0

        game.teamAScore = currentScoreA
        game.teamBScore = currentScoreB


        teamAScore = findViewById(R.id.teamAScore)
        teamBScore = findViewById(R.id.teamBScore)
        updateScores();



        teamAThreePointButton.setOnClickListener { view: View ->
            game.addScoreTeamA(3);
            updateScores()
        }
        teamATwoPointButton.setOnClickListener { view: View ->
            game.addScoreTeamA(2);
            updateScores()
        }
        teamAFreethrowButton.setOnClickListener { view: View ->
            game.addScoreTeamA(1);
            updateScores()
        }
        teamBThreePointButton.setOnClickListener { view: View ->
            game.addScoreTeamB(3);
            updateScores()
        }
        teamBTwoPointButton.setOnClickListener { view: View ->
            game.addScoreTeamB(2);
            updateScores()
        }
        teamBFreethrowButton.setOnClickListener { view: View ->
            game.addScoreTeamB(1);
            updateScores()
        }
        resetButton.setOnClickListener { view: View ->
            game.reset()
            updateScores()
        }

        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val basketballGameViewModel = provider.get(BasketballGameViewModel::class.java)

        }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onPause() called")
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("MainActivity", "onSaveInstanceState(savedInstanceState: Bundle) called");

        savedInstanceState.putInt(SCORE_A, game.getScoreTeamA())
        savedInstanceState.putInt(SCORE_B, game.getScoreTeamB())
    }

    private fun updateScores() {
        teamAScore.setText(game.getScoreTeamA().toString())
        teamBScore.setText(game.getScoreTeamB().toString())
    }
}