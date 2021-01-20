package com.bawp.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighestScore(int score){
        int currentScore = score;
        int lastScore=preferences.getInt("highScore", 0);

        if (currentScore>lastScore){
            preferences.edit().putInt("highScore", currentScore).apply();
        }
    }

    public int getHighScore() {
        return preferences.getInt("highScore", 0);
    }
}
