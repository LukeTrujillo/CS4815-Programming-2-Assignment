package lrtrujillo.cs4518_programming_2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private const val SCORE_A = "score_a"
private const val SCORE_B = "score_b"

private const val CUTE_DOG_REQUEST_CODE = 1;

/**
 * A simple [Fragment] subclass.
 * Use the [ScoreboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreboardFragment : Fragment() {

    private val TAG = "ScoreboardFragment";


    interface Callbacks {
        fun onDisplayPressed(winningTeam:String);
    }


    private var callbacks: Callbacks? = null

    private lateinit var game: BasketballGame;

    private lateinit var teamAThreePointButton: Button
    private lateinit var teamATwoPointButton: Button
    private lateinit var teamAFreethrowButton: Button

    private lateinit var teamBThreePointButton: Button
    private lateinit var teamBTwoPointButton: Button
    private lateinit var teamBFreethrowButton: Button

    private lateinit var saveButton: Button;
    private lateinit var displayButton: Button;

    private lateinit var resetButton: Button

    private lateinit var teamAScore: TextView
    private lateinit var teamBScore: TextView

    private lateinit var teamAName: TextView
    private lateinit var teamBName: TextView

    private val gameViewModel: BasketballGameViewModel by lazy {
        ViewModelProviders.of(this).get(BasketballGameViewModel::class.java)
    }


    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

        this.game = BasketballGame()


        if(param1 != null && arguments != null) {
            val gameUUID: UUID = UUID.fromString(arguments?.getSerializable(ARG_PARAM1).toString())
            gameViewModel.loadGame(gameUUID)
        }

        Log.d(TAG, "onCreate(savedInstanceState: Bundle?) called");

    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("MainActivity", "onSaveInstanceState(savedInstanceState: Bundle) called");

        savedInstanceState.putInt(SCORE_A, game.teamAScore)
        savedInstanceState.putInt(SCORE_B, game.teamBScore)
    }

    private fun updateScores() {
        teamAName.setText(game.teamAName)
        teamBName.setText(game.teamBName)

        teamAScore.setText(game.teamAScore.toString())
        teamBScore.setText(game.teamBScore.toString())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context);
        callbacks = context as Callbacks
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() called.");
        val view = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        teamAThreePointButton = view.findViewById(R.id.teamAThreePointBtn) as Button
        teamATwoPointButton = view.findViewById(R.id.teamATwoPointBtn) as Button
        teamAFreethrowButton = view.findViewById(R.id.teamAFreeThrowBtn) as Button

        teamBThreePointButton = view.findViewById(R.id.teamBThreePointBtn) as Button
        teamBTwoPointButton = view.findViewById(R.id.teamBTwoPointBtn) as Button
        teamBFreethrowButton = view.findViewById(R.id.teamBFreeThrowBtn) as Button

        displayButton = view.findViewById(R.id.displayButton) as Button
        saveButton = view.findViewById(R.id.saveButton) as Button

        resetButton = view.findViewById(R.id.resetButton) as Button

        val currentScoreA = savedInstanceState?.getInt(SCORE_A, 0) ?: 0
        val currentScoreB = savedInstanceState?.getInt(SCORE_B, 0) ?: 0

        game.teamAScore = currentScoreA
        game.teamBScore = currentScoreB

        teamAScore = view.findViewById(R.id.teamAScore)
        teamBScore = view.findViewById(R.id.teamBScore)

        teamAName = view.findViewById(R.id.teamALabel)
        teamBName = view.findViewById(R.id.teamBLabel)


        if(param1 != null) {
            Log.d(TAG, "Param1 (UUID) received")
            Log.d(TAG, "Game loaded ${game.teamAName} vs ${game.teamBName} (${game.teamAScore} - ${game.teamBName})")

            teamAName.setText(game.teamAName)
            teamAName.setText(game.teamBName)
        }

        updateScores();

        // Inflate the layout for this fragment
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.gameLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { game ->
                game?.let {
                    this.game = game;
                    updateScores()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart();

        saveButton.setOnClickListener {view: View ->

            Log.d(TAG, "saveButton.setOnClickListenerbed()");

            val intent = Intent(activity, CuteDogActivity::class.java);
            intent.putExtra("TEAM_A_NAME", teamALabel.text);
            intent.putExtra("TEAM_B_NAME", teamBLabel.text);
            intent.putExtra("TEAM_A_SCORE", game.teamAScore);
            intent.putExtra("TEAM_B_SCORE", game.teamBName);

            startActivityForResult(intent, CUTE_DOG_REQUEST_CODE);
        }

        teamAThreePointButton.setOnClickListener { view: View ->
            game.teamAScore += 3
            updateScores()
        }
        teamATwoPointButton.setOnClickListener { view: View ->
            game.teamAScore += 2
            updateScores()
        }
        teamAFreethrowButton.setOnClickListener { view: View ->
            game.teamAScore += 1
            updateScores()
        }
        teamBThreePointButton.setOnClickListener { view: View ->
            game.teamBScore += 3
            updateScores()
        }
        teamBTwoPointButton.setOnClickListener { view: View ->
            game.teamBScore += 2
            updateScores()
        }
        teamBFreethrowButton.setOnClickListener { view: View ->
            game.teamBScore += 1
            updateScores()
        }
        resetButton.setOnClickListener { view: View ->
            game.teamBScore = 0
            game.teamAScore = 0
            updateScores()
        }
        displayButton.setOnClickListener { view: View ->

            gameViewModel.saveGame(game)

            if(game.teamBScore > game.teamAScore)
                callbacks?.onDisplayPressed("Team B");
            else
                callbacks?.onDisplayPressed("Team A");
        }
        saveButton.setOnClickListener{view: View ->
            gameViewModel.saveGame(game)
            Toast.makeText(context, "Game saved", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScoreboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ScoreboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}