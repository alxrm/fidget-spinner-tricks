package rm.com.fidgetspinnertricks.data.entity;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.List;

/**
 * Created by alex
 */

public final class LeagueTricks {

  public final String league;
  public final List<Trick> tricks;

  public LeagueTricks(@NonNull String league, @NonNull List<Trick> tricks) {
    this.league = league;
    this.tricks = tricks;
  }

  public static LeagueTricks of(@NonNull String league, @NonNull List<Trick> tricks) {
    return new LeagueTricks(league, tricks);
  }

  public static LeagueTricks empty() {
    return new LeagueTricks("", Collections.<Trick>emptyList());
  }
}
