package lrtrujillo.cs4518_programming_2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
   private val TAG = "MainActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_scoreboard);

        if(currentFragment == null) {
            val fragment = ScoreboardFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_scoreboard, fragment).commit()
        }*/

        val currentFragment = supportFragmentManager.findFragmentById(R.id.basketball_game_recycler_view);

        if(currentFragment == null) {
            val fragment = BasketballGameFragmentList.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.basketball_game_recycler_view, fragment).commit()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult() called");

        if(resultCode == Activity.RESULT_OK) {
          Toast.makeText(this, R.string.cute_dog, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.ugly_dog, Toast.LENGTH_SHORT).show()
        }
    }
}