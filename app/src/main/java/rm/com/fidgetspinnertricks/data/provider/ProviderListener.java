package rm.com.fidgetspinnertricks.data.provider;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public interface ProviderListener<T> {
  void onProviderResult(@NonNull T payload);
}