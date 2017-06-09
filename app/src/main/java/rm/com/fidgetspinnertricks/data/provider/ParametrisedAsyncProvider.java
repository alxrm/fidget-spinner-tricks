package rm.com.fidgetspinnertricks.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

abstract public class ParametrisedAsyncProvider<P, T> extends AbstractAsyncProvider<T> {
  @NonNull final Queue<P> paramsQueue = new LinkedList<>();

  public ParametrisedAsyncProvider(@NonNull ExecutorService executor,
      @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  @Override public void provide(@NonNull T defaultValue, @NonNull ProviderListener<T> callback) {
    super.provide(defaultValue, callback);
  }

  public void provide(@NonNull P param, @NonNull T defaultValue,
      @NonNull ProviderListener<T> callback) {
    paramsQueue.add(param);
    super.provide(defaultValue, callback);
  }
}
