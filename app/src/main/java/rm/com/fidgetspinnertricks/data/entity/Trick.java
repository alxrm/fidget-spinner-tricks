package rm.com.fidgetspinnertricks.data.entity;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public final class Trick {

  public final String title;
  public final String path;
  public final int level;

  public Trick(@NonNull String title, int level, @NonNull String path) {
    this.title = title;
    this.level = level;
    this.path = path;
  }
}
