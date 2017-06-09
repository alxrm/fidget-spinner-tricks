package rm.com.fidgetspinnertricks.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.ExecutorService;
import rm.com.fidgetspinnertricks.util.Preconditions;

@SuppressWarnings("WeakerAccess") abstract public class AbstractAsyncProvider<T>
    implements AsyncProvider<T> {

  protected final ExecutorService executor;
  protected final Handler mainThreadHook;

  public AbstractAsyncProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    this.executor = executor;
    this.mainThreadHook = mainThreadHook;
  }

  @Override
  public void provide(@NonNull final T defaultValue, @NonNull final ProviderListener<T> callback) {
    Preconditions.checkNotNull(callback);

    executor.submit(new Runnable() {
      @Override public void run() {
        final T result = execute();

        postCallback(result != null ? result : defaultValue, callback);
      }
    });
  }

  @Nullable protected abstract T execute();

  protected void postCallback(@NonNull final T result,
      @NonNull final ProviderListener<T> callback) {
    mainThreadHook.post(new Runnable() {
      @Override public void run() {
        callback.onProviderResult(result);
      }
    });
  }
}
