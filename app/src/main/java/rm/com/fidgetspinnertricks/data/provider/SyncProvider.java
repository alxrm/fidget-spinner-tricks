package rm.com.fidgetspinnertricks.data.provider;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

/**
 * Created by alex
 */

public interface SyncProvider<T> {
  @WorkerThread @NonNull T provide(@NonNull T defaultValue);
}
