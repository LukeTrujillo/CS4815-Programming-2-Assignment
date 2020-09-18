package lrtrujillo.cs4518_programming_2

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class CuteDogActivity : AppCompatActivity() {

    private val TAG = "CuteDogActivity";

    private lateinit var cuteDogButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cute_dog)

        Log.d(TAG, "onCreate() called");

        cuteDogButton = findViewById(R.id.cuteDogBtn);


        cuteDogButton.setOnClickListener {view: View ->
            Log.d(TAG, "cuteDogButton.setOnClickListener()");

            cuteDogButton.setEnabled(false);
            setResult(Activity.RESULT_OK);
        }

    }
}