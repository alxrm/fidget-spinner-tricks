package rm.com.fidgetspinnertricks.data;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import rm.com.fidgetspinnertricks.AdCoordinator;

/**
 * Created by alex
 */

public final class PreferencesAdCoordinator implements AdCoordinator {
  private static final String AD_COUNTER_PREF_KEY = "AD_COUNTER_PREF_KEY";
  private static final int AD_COUNTER_DEFAULT_VALUE = 0;

  private final SharedPreferences preferences;
  private final SharedPreferences.Editor editor;

  @SuppressLint("CommitPrefEdits")
  public PreferencesAdCoordinator(@NonNull SharedPreferences preferences) {

    this.preferences = preferences;
    this.editor = preferences.edit();

    editor.putInt(AD_COUNTER_PREF_KEY, AD_COUNTER_DEFAULT_VALUE).apply();
  }

  @Override public void visited() {
    editor.putInt(AD_COUNTER_PREF_KEY, internalAdCounter() + 1).apply();
  }

  @Override public boolean shouldShowTheAd() {
    // we show the ad every time the counter number is odd
    return internalAdCounter() % 2 != 0;
  }

  private int internalAdCounter() {
    return preferences.getInt(AD_COUNTER_PREF_KEY, AD_COUNTER_DEFAULT_VALUE);
  }
}
