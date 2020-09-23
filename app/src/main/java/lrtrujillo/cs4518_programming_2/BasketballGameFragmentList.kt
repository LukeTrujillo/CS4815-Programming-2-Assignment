package lrtrujillo.cs4518_programming_2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_basketball_game.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_BASKETBALL_GAME_ID = "basketball_game_id"

/**
 * A simple [Fragment] subclass.
 * Use the [BasketballGameFragmentList.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasketballGameFragmentList : Fragment() {
    // TODO: Rename and change types of parameters
    private var winningTeam: String? = null
    private val TAG:String = "BasketballGameFragment";

    private var adapter: BasketballGameAdapter? = BasketballGameAdapter(emptyList())

    private lateinit var basketballGameRecyclerView: RecyclerView;

    private val basketGameListViewModel: BasketballGameListViewModel by lazy {
        ViewModelProviders.of(this).get(BasketballGameListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.winningTeam = arguments?.getString(ARG_BASKETBALL_GAME_ID);
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_basketball_game_list, container, false)

        basketballGameRecyclerView = view.findViewById(R.id.basketball_game_recycler_view)
        basketballGameRecyclerView.layoutManager = LinearLayoutManager(context)

        basketballGameRecyclerView.adapter = adapter

        return view;
    }
    private fun update(games: List<BasketballGame>) {
        adapter = BasketballGameAdapter(games);
        basketballGameRecyclerView.adapter = adapter;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(this.winningTeam == null) {
            basketGameListViewModel.basketballGameListLiveData.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { games ->
                    games?.let {
                        Log.d(TAG, "Got ${games.size} basketball game");
                        update(games);
                    }
                }
            )
        } else if (this.winningTeam == "Team A") {
            basketGameListViewModel.basketballGameListTeamAWins.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { games ->
                    games?.let {
                        Log.d(TAG, "Got ${games.size} Team A wins");
                        update(games);
                    }
                }
            )
        } else {
            basketGameListViewModel.basketballGameListTeamBWins.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { games ->
                    games?.let {
                        Log.d(TAG, "Got ${games.size} Team B wins");
                        update(games);
                    }
                }
            )
        }
    }


    private inner class BasketballGameHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timestampText: TextView = itemView.findViewById(R.id.date);
        val teamText: TextView = itemView.findViewById(R.id.team);
        val scoreText: TextView = itemView.findViewById(R.id.score);

        val winningIcon: ImageView = itemView.findViewById(R.id.winningTeamIcon);
    }

    private inner class BasketballGameAdapter(var games: List<BasketballGame>) : RecyclerView.Adapter<BasketballGameHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketballGameHolder {
            val view = layoutInflater.inflate(R.layout.list_item_basketball_game, parent, false)
            return BasketballGameHolder(view)
        }

        override fun getItemCount(): Int {
            return games.size
        }

        override fun onBindViewHolder(holder: BasketballGameHolder, position: Int) {
            val game = games[position]

            holder.apply {
                val simple: DateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
                val result = Date(game.timestamp)
                timestampText.setText(simple.format(result));

                teamText.setText("Team ${game.teamAName} : Team ${game.teamBName}");
                scoreText.setText("${game.teamAScore}:${game.teamBScore}");

                if(game.teamAScore < game.teamBScore) {
                    //set team b score icon
                    winningIcon.setImageResource(R.drawable.heat)
                } else {
                    winningIcon.setImageResource(R.drawable.celtics)
                }
            }
        }

    }


    companion object {

        fun newInstance() : BasketballGameFragmentList {
            return BasketballGameFragmentList()
        }

        fun newInstance(winningTeam: String) : BasketballGameFragmentList {
            val args = Bundle().apply {
                putSerializable(ARG_BASKETBALL_GAME_ID, winningTeam)
            }

            return BasketballGameFragmentList().apply {
                arguments = args
            }
        }
    }
}