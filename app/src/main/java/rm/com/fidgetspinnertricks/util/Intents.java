package rm.com.fidgetspinnertricks.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import static android.content.Intent.ACTION_SEND;

/**
 * Created by alex
 */

public final class Intents {
  private Intents() {
  }

  public static void share(@NonNull Context context, @NonNull String message) {
    final Intent intent = new Intent(ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, message);
    context.startActivity(Intent.createChooser(intent, null));
  }
}
