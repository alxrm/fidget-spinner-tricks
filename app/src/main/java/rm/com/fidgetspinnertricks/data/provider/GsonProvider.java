package rm.com.fidgetspinnertricks.data.provider;

import android.content.res.AssetManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import java.util.concurrent.ExecutorService;
import rm.com.fidgetspinnertricks.util.Files;
import rm.com.fidgetspinnertricks.util.Preconditions;

/**
 * Created by alex
 */

public abstract class GsonProvider<T> {

  protected final ExecutorService executor;
  protected final AssetManager assets;
  protected final Handler mainThreadHandler;
  protected final Gson gson;

  protected volatile T cachedResult;

  public GsonProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull Gson gson, @NonNull AssetManager assets) {
    this.executor = executor;
    this.mainThreadHandler = mainThreadHandler;
    this.gson = gson;
    this.assets = assets;
  }

  public void provide(@NonNull final String path, @NonNull final ProviderListener<T> callback) {
    Preconditions.checkNotNull(callback);

    executor.submit(new Runnable() {
      @Override public void run() {
        final String raw = Files.readText(assets, path);
        final T result = parse(raw != null ? raw : "");

        Preconditions.checkNotNull(result, "Result cannot be null");

        cachedResult = result;
        sendResult(result, callback);
      }
    });
  }

  @NonNull protected abstract T parse(@NonNull String src);

  protected void sendResult(@NonNull final T result, @NonNull final ProviderListener<T> callback) {
    mainThreadHandler.post(new Runnable() {
      @Override public void run() {
        callback.onProvide(result);
      }
    });
  }
}