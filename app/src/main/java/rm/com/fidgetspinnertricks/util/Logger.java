package rm.com.fidgetspinnertricks.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

@SuppressWarnings("ALL") public final class Logger {
  private static final String LEVEL_DEBUG = "DEBUG";
  private static final String LEVEL_ERROR = "ERROR";

  private Logger() {
  }

  public static void d(@Nullable Object msg) {
    d(String.valueOf(msg != null ? msg : "null"));
  }

  public static void d(@Nullable String msg) {
    Log.e(LEVEL_DEBUG, "" + msg);
  }

  public static void e(@Nullable String err) {
    Log.e(LEVEL_ERROR, "" + err);
  }

  public static void e(@NonNull Throwable err) {
    e(err.getClass().getName());
  }
}
