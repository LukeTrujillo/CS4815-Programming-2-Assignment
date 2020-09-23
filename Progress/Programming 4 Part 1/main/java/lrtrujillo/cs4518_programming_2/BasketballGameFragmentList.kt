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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasketballGameFragmentList.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasketballGameFragmentList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val TAG:String = "BasketballGameFragment";

    private var adapter: BasketballGameAdapter? = null;

    private lateinit var basketballGameRecyclerView: RecyclerView;

    private val basketGameListViewModel: BasketballGameListViewModel by lazy {
        ViewModelProviders.of(this).get(BasketballGameListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Total Games ${basketGameListViewModel.gameList.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_basketball_game_list, container, false)

        basketballGameRecyclerView = view.findViewById(R.id.basketball_game_recycler_view)
        basketballGameRecyclerView.layoutManager = LinearLayoutManager(context)

        update();
        return view;
    }
    private fun update() {
        val games = basketGameListViewModel.gameList;
        adapter = BasketballGameAdapter(games);
        basketballGameRecyclerView.adapter = adapter;
    }


    private inner class BasketballGameHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timestampText: TextView = itemView.findViewById(R.id.date);
        val teamText: TextView = itemView.findViewById(R.id.team);
        val scoreText: TextView = itemView.findViewById(R.id.score);

        val winningIcon: ImageView = itemView.findViewById(R.id.winningTeamIcon);
    }

    private inner class BasketballGameAdapter(var games: List<BasketballGameViewModel>) : RecyclerView.Adapter<BasketballGameHolder>() {
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
    }
}