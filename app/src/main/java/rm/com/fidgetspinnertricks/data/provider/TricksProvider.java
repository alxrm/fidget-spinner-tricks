package rm.com.fidgetspinnertricks.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.fidgetspinnertricks.data.entity.Trick;
import rm.com.fidgetspinnertricks.data.entity.Trick_Table;
import rm.com.fidgetspinnertricks.util.Logger;
import rm.com.fidgetspinnertricks.util.Preconditions;
import rm.com.fidgetspinnertricks.util.Transactions;

/**
 * Created by alex
 */

public final class TricksProvider
    extends ParametrisedAsyncProvider<String, Pair<String, List<Trick>>> {
  @NonNull private final TricksGsonProvider gsonProvider;

  private final Pair<String, List<Trick>> defaultValue =
      Pair.create("", Collections.<Trick>emptyList());

  private boolean isDatabaseReady = false;

  public TricksProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook,
      @NonNull TricksGsonProvider gsonProvider) {
    super(executor, mainThreadHook);
    this.gsonProvider = gsonProvider;
  }

  public final void provide(@NonNull String param,
      @NonNull ProviderListener<Pair<String, List<Trick>>> callback) {
    provide(param, defaultValue, callback);
  }

  @Nullable @Override protected Pair<String, List<Trick>> execute() {
    Preconditions.checkNotNull(paramsQueue);

    if (!isDatabaseReady) {
      Transactions.saveAllBlocking(gsonProvider.provideOrDefault(Collections.<Trick>emptyList()));
      isDatabaseReady = true;
    }

    final String league = paramsQueue.poll();
    Logger.d("Querying: " + league);

    return Pair.create(league, tricksByLeague(league));
  }

  @NonNull private List<Trick> tricksByLeague(String leagueName) {
    return SQLite.select()
        .from(Trick.class)
        .where(Trick_Table.league.eq(leagueName))
        .orderBy(Trick_Table.level, true)
        .queryList();
  }
}
