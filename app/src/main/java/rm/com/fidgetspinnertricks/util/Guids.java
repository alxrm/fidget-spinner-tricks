package rm.com.fidgetspinnertricks.util;

import android.support.annotation.NonNull;
import java.util.UUID;

public final class Guids {

  private Guids() {
  }

  @NonNull public static String randomGuid() {
    return UUID.randomUUID().toString();
  }
}
