package lrtrujillo.cs4518_programming_2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity(), ScoreboardFragment.Callbacks, BasketballGameFragmentList.Callbacks {
   private val TAG = "MainActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container);

        if(currentFragment == null) {
            val fragment = ScoreboardFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
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

    override fun onBasketballGamePressed(uuid: UUID) {
        Log.d(TAG, "onBasketballGamePressed(${uuid.toString()})h");
        val fragment = ScoreboardFragment.newInstance(uuid.toString()); //set the new team to the fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    override fun onDisplayPressed(winningTeam: String) {
        Log.d(TAG, "MainActivity.onDisplayPressed(${winningTeam})");

        val fragment = BasketballGameFragmentList.newInstance(winningTeam)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}