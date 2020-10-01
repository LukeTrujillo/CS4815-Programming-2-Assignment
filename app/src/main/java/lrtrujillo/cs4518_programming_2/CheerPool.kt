package lrtrujillo.cs4518_programming_2

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.media.SoundPool.Builder
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

private const val TAG = "CheerPool"
private const val SOUNDS_FOLDER = "cheering_sound"
private const val MAX_SOUNDS = 2

class CheerPool(private val assests: AssetManager) {
    val sounds: List<Sound>

    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build();

    init {
        sounds = loadSounds()
    }


    fun loadSounds() : List<Sound> {
        val soundNames: Array<String>
        try {
          soundNames = assests.list(SOUNDS_FOLDER) !!
        } catch(e: Exception) {
            Log.d(TAG, "Could not find list assests", e)
            return emptyList();
        }

        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)

            try {
                load(sound)
                sounds.add(sound)
                Log.d(TAG, "Successfully loaded $filename")
            } catch(ioe: IOException) {
                Log.e(TAG, "Cannot load sound $filename", ioe)
            }
        }

        return sounds;
    }

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assests.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId

        Log.d(TAG, "${sound.name} has a soundId = ${sound.soundId}")
    }

    fun play(sound: Sound) {
        Log.d(TAG, "play(sound) called name=${sound.name} id=${sound.soundId}")

        sound.soundId?.let {
            Log.d(TAG, "Playing sound")
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }


}