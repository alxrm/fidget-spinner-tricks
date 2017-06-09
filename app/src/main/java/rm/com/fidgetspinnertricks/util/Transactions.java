package rm.com.fidgetspinnertricks.util;

import android.support.annotation.Nullable;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import java.util.List;
import rm.com.fidgetspinnertricks.data.FidgetDatabase;

/**
 * Created by alex
 */

public final class Transactions {
  private Transactions() {
  }

  public static <T extends BaseModel> void saveAllBlocking(@Nullable final List<T> items) {
    if (items == null || items.isEmpty()) {
      return;
    }

    FlowManager.getDatabase(FidgetDatabase.class).executeTransaction(new ITransaction() {
      @Override public void execute(DatabaseWrapper databaseWrapper) {
        final int size = items.size();

        for (int i = 0; i < size; i++) {
          final T item = items.get(i);

          if (!item.exists(databaseWrapper)) {
            item.insert(databaseWrapper);
          }
        }
      }
    });
  }
}
