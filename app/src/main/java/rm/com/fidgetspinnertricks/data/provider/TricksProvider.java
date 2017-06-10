package rm.com.fidgetspinnertricks.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.fidgetspinnertricks.data.entity.LeagueTricks;
import rm.com.fidgetspinnertricks.data.entity.Trick;
import rm.com.fidgetspinnertricks.data.entity.Trick_Table;
import rm.com.fidgetspinnertricks.util.Transactions;

/**
 * Created by alex
 */

public final class TricksProvider extends ParametrisedAsyncProvider<String, LeagueTricks> {
  private final SyncProvider<List<Trick>> allTricksProvider;
  private boolean isDatabaseReady = false;

  public TricksProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook,
      @NonNull SyncProvider<List<Trick>> allTricksProvider) {
    super(executor, mainThreadHook);
    this.allTricksProvider = allTricksProvider;
  }

  @Nullable @Override protected LeagueTricks execute() {
    final String league = paramsQueue.poll();

    if (!isDatabaseReady) {
      Transactions.saveAllBlocking(allTricksProvider.provide(Collections.<Trick>emptyList()));
      isDatabaseReady = true;
    }

    return LeagueTricks.of(league, tricksByLeague(league));
  }

  @NonNull private List<Trick> tricksByLeague(String leagueName) {
    return SQLite.select()
        .from(Trick.class)
        .where(Trick_Table.league.eq(leagueName))
        .orderBy(Trick_Table.level, true)
        .queryList();
  }
}
