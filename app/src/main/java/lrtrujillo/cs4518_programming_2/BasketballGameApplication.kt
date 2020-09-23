package lrtrujillo.cs4518_programming_2

import android.app.Application
import lrtrujillo.cs4518_programming_2.repo.BasketballGameRepository

class BasketballGameApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BasketballGameRepository.initialize(this);
    }
}