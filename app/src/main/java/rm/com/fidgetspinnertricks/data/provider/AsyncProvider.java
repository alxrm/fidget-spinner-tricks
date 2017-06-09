package rm.com.fidgetspinnertricks.data.provider;

import android.support.annotation.NonNull;

public interface AsyncProvider<T> {
  void provide(@NonNull T defaultValue, @NonNull ProviderListener<T> callback);
}
